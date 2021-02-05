package idGeneration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idGeneration.Exceptions.*;
import java.util.List;
import java.util.Optional;

//Create Id service class to code the business logic and it acts as a middle layer between repository and controller class.

//Note: @Transactional is used annotate methods are executed in transactions. When you are updating several tables and need rollback due to an update failed somewhere. In this app, it is not required.

@Service
public class IdService {

	@Autowired
	private IdRepository idRepository;
	@Autowired
	private CategoryRepository categRepository;
	  
	public List<TokenId> listAllUser() throws TokenNotFoundException {
	  
		List<TokenId> tokenList = idRepository.findAll();
		if (tokenList.isEmpty()) {
			throw new TokenNotFoundException("No matching record!!");
		}
		else {
			return tokenList;
		}
	}

	public TokenId saveUser(TokenId tokid) {
	    return idRepository.save(tokid); //returns the saved entity
	}

	 public TokenId getUser(String tid) throws TokenNotFoundException {
	
		Optional<TokenId> tokid = idRepository.findById(tid); //returns either the entity or Optional.empty
        if (!tokid.isPresent()) {
        	throw new TokenNotFoundException("The input token id does not exist in the database ::" + tid);
        }
        return tokid.get(); 
		
//or, you could do something like this too...
//		TokenId tokid = idRepository.findById(tid).get();
//		if (tokid == null)
//			throw new TokenNotFoundException("The input token id does not exist in the database ::" + tid);
//		return tokid;
		
	 }

	 
	public void deleteUser(String tid) throws TokenNotFoundException {
		//Below code line throws EmptyResultDataAccessException if tid does not exist in the table "tokenid". 
		//If you use this, you will have to catch EmptyResultDataAccessException in the calling class (IDController).
		//idRepository.deleteById(tid); 
		
		//*******If you don't want to catch EmptyResultDataAccessException in IDController class, use below code
		if (idRepository.existsById(tid)) {
			idRepository.deleteById(tid);
		 }
		 else {
			throw new TokenNotFoundException("The input token id does not exist in the database ::" + tid);
		 }
			
		
	}
	
//******************below code is replaced by the use of NewTokenValidator.java******************
//	public boolean checkIfTokenFound(String tid) throws TokenNotFoundException {
//		
//		//Returns true if token id does exist in the table "tokenid" (otherwise throw the error)
//		if (!idRepository.existsById(tid)) {
//			throw new TokenNotFoundException("The input token id does not exist in the database ::" + tid);
//		}
//		
//		return true;
//		
//	}

	
	public boolean checkIfValidCategory(String categ) throws CategNotFoundException {
		
		//Returns the "Optional" corresponding to the input 'tokenId', if it does exist in the table "tokenid" (otherwise Optional#Empty)
		//		Optional<Pattern> pt = categRepository.findById(categ);
		//		if (!pt.isPresent()) {
		
		
		//Returns true if category is setup in the "pattern" table (otherwise throw the error)
		if (!categRepository.existsById(categ)) {
			throw new CategNotFoundException("Category is not setup in the DB table ::" + categ);
		}
		
		return true;
		
	}
	

	
	public boolean checkIfIdUniqNotInput(String idUniq) throws CategNotFoundException {
		if (idUniq != null) { //do I need to check isEmpty() ?
			throw new CategNotFoundException("The request must not contain unique id"); //is it ok to use CategNotFoundException ?
		}
		return true;
	}
	
	
	public String generateUniqueId(TokenId tokid) {

		String patt_code = " ";
		
		//Returns the maximum value of id_uniq column from the table "tokenid" based upon the input 'category' if existing already. It returns Null if the input 'category' does not exist in "tokenid" table.
		String lastId = idRepository.findIdByCategory(tokid.getCategName()); 
			
		//if a category was never recorded in "tokenid" before, fetch the pattern code value from "pattern" table otherwise, 
		//get the last unique id for this category from "tokenid" and increment that by 1 to generate the new unique id
		if (lastId == null) { 
			
			//Returns the Entity (Pattern table record-->Pattern object) corresponding to the input 'category'
			Pattern p = categRepository.findById(tokid.getCategName()).get();   /////if not found in pattern table also ???
			patt_code = p.getPatternCode(); 
			
		}
		else {  
			
			String[] parts = lastId.split("-");
			patt_code = parts[0] + "-" + (Integer.parseInt(parts[1]) +1);
			
		}
					
		return patt_code;
	} 
	
}



  

