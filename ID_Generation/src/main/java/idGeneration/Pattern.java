package idGeneration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

//@Entity is used to annotate that the class is an entity in the database. 
//@Table is used to annotate name of table in the database. 
//@GeneratedValue is used to generation strategies for the values of primary keys.

//Pattern model class to map with the "pattern" table.

@Entity
@Table(name="pattern")
public class Pattern {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="categ_name")
	private String categName;
	
	@Column (name = "pattern_code")
	private String patternCode;
	
	@Column (name = "created_time")
	private String createdTime;
	
	@Column (name = "updated_time")
	private String updatedTime;
	
	
	public Pattern() {
		
	}
	
	public Pattern (String categName, String patternCode, String createdTime, String updatedTime) {
		this.categName = categName;
		this.patternCode = patternCode;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}
	
	
	public String getCategName() {
		return categName;
	}

	public void setCategName(String categName) {
		this.categName = categName;
	}

	public String getPatternCode() {
		return patternCode;
	}

	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

		
}
