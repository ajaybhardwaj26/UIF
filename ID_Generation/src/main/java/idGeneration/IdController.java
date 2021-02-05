package idGeneration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javassist.NotFoundException;
import idGeneration.Exceptions.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/tokens")

public class IdController {
	@Autowired				//to wire the IdService into IdController
	IdService idService;
	
	@GetMapping("/")
	public ResponseEntity <List<TokenId>> list() throws TokenNotFoundException {
		
		List<TokenId> tokenList = idService.listAllUser(); 
		//return new ResponseEntity <List<TokenId>>(tokenList, HttpStatus.OK); --> this also works
		
		return ResponseEntity.ok(tokenList);
		
	}

	@GetMapping("/{tid}")
	public ResponseEntity<TokenId> get(@PathVariable String tid) throws TokenNotFoundException {
		
		TokenId tokid = idService.getUser(tid);
		//return new ResponseEntity<TokenId>(tokid, HttpStatus.OK); --> this also works
		
		return ResponseEntity.ok(tokid);
	        
	}

	
	@PostMapping("/create")  	
	public ResponseEntity<TokenId> add(@Validated(TokenId.PostValidation.class) @RequestBody TokenId tokid) throws TokenFoundException, CategNotFoundException  {	
		//Generate auto-incremented unique id (id_uniq in "tokenid" table) using the pattern code (input via Postman along with token id) 
		//"pattern" is a static table having category names and unique pattern code for each category.
		//Postman request contains - token id and the category name, the purpose is to generate the id value (id_uniq) for this input combination 

		//Rules:
			// i) If token id already exists in "tokenid" table - Do NOT generate the id [checkIfTokenExists()]
			// ii) If token id does NOT already exist in "tokenid" table - Generate the id as following:
			//	****a. If category does NOT exist in "tokenid" table - fetch the corresponding pattern code value from "pattern" table
			//  ****b. If category does already exist in "tokenid" table - fetch the highest id_uniq value from "tokenid" table and increment this to generate the new id (id_uniq column) for this input token+category
			// iii) Token id & Category are mandatory to input 
			// iv) Category must be valid i.e. it must exist in "pattern" table
			// v) If incoming JSON request contains the unique id, ignore that. Program should auto generate and store this auto generated value.
	
		String tid = tokid.getTokenId();
		
		if (idService.checkIfIdUniqNotInput(tokid.getIdUniq())) {	//unique-id should not be input	
				//******Below check is not required as we are checking in NewTokenValidator.java via @NewToken attached in entity class - TokenId.java
				//if (idService.checkIfTokenNotFound(tid)) { //if the input token already exists in "tokenid" table, throw the error
						if (idService.checkIfValidCategory(tokid.getCategName())) { //if the input category does not exist in "pattern" table, throw an error
			
							//At this point, we validated the input token id value and the category - both are valid ones. So, proceed with the id generation process.
							String patternCode = idService.generateUniqueId(tokid);
			
							//Set the new unique id
							tokid.setIdUniq(patternCode);
			
							//Save the new token with category and the unique id in "tokenid" table
							final TokenId newToken = idService.saveUser(tokid);
							return ResponseEntity.ok().body(tokid);
							
						}
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		//return null; 
	}
	
	
	 @PutMapping("/update")  
	 public ResponseEntity<?> update(@Validated(TokenId.PutValidation.class) @RequestBody TokenId tokid) throws TokenNotFoundException, CategNotFoundException {
		 //Rules: 
		 // a) token id and category name both are mandatory to input.  
		 // b) token id must be an already existing one in "tokenid" table. 
		 // c) only category name can be changed which must be an existing one in "pattern" table (new category name must be valid too)
		 // d) PUT request can't have unique-id in the JSON request body
		 String tid = tokid.getTokenId();
		 String categ = tokid.getCategName();
		 
		// if (idService.checkIfTokenInput(tid)) {							//token id should be input
		//	 if (idService.checkIfCategoryInput(categ)) {					//category name should be input
				 if (idService.checkIfIdUniqNotInput(tokid.getIdUniq())) {	//unique-id should not be input
					if (idService.checkIfValidCategory(categ)) { //if the input category does not exist in "pattern" table, throw an error	 
							 TokenId retrievedToken = idService.getUser(tid); 	//get the existing Token record from "tokid" table 
							 retrievedToken.setCategName(categ);				//change its category to the input category
							 final TokenId updatedToken = idService.saveUser(retrievedToken);   //Updates only the category name in the table
							 return ResponseEntity.ok(updatedToken);
						 }
					 
				}
				 
	 
		return new ResponseEntity<>(HttpStatus.OK);
		//return null;  
		
	 }
	   
	
	@DeleteMapping("/delete/{tid}")
    public ResponseEntity<?> delete(@PathVariable String tid) throws TokenNotFoundException {
        
		idService.deleteUser(tid); //deletes if input token already exists in "tokenid" table otherwise throw the error
		
        return new ResponseEntity<>(HttpStatus.OK);
        
		
    }

	
	 
}
