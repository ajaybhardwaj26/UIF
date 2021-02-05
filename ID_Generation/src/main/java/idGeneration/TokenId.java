package idGeneration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import idGeneration.Validations.ExistingToken;
import idGeneration.Validations.NewToken;

//@Entity is used to annotate that the class is an entity in the database. 
//@Table is used to annotate name of table in the database. 
//@GeneratedValue is used to generation strategies for the values of primary keys.

//TokenId model class to map with the "tokenid" table.

@Entity
@Table(name="tokenid")
public class TokenId {

	@Id
	@NotBlank (message="Token ID must be not null", groups = {PostValidation.class, PutValidation.class})
	@NewToken(groups=PostValidation.class)		//POST (creating a new user)--> incoming token must not already be present in DB table ("tokenid")
	@ExistingToken(groups=PutValidation.class)	//PUT (updating an existing user)--> incoming token id must be the existing one
	@Column(name="token_id")
	private String tokenId;
	
	@NotBlank (message = "Category name must be not null", groups = {PostValidation.class, PutValidation.class})
	@Column (name = "categ_name")
	private String categName;
	
	//@NotBlank(groups=PutValidation.class)	//For updating, we would require a non-null id of the object.
	//@Null (groups=PostValidation.class)		//For creating, id should be null (program should auto-generate this).
	//The request must not contain unique id"
	@Column (name = "id_uniq")
	private String idUniq;		  
	
	
	public TokenId() {
		
	}
	
	public TokenId (String tokenId, String categName, String idUniq) {
		this.tokenId = tokenId;
		this.categName = categName;
		this.idUniq = idUniq;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getCategName() {
		return categName;
	}

	public void setCategName(String categName) {
		this.categName = categName;
	}
	
	public String getIdUniq() {
		return idUniq;
	}
	
	public void setIdUniq(String idUniq) {
		this.idUniq = idUniq;
	}

	public interface PutValidation {
		
	}
	
	public interface PostValidation {
		
	}
	
			
}
