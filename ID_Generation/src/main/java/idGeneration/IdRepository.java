package idGeneration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//Create Id Repository interface extending JPA Repository.
//There are built-in methods for CRUD operations in JpaRepository,writing any SQL query is not needed.

//TokenId - the class name related to the table "tokenid"
public interface IdRepository extends JpaRepository<TokenId,String>{

    @Query("SELECT max(t.idUniq) FROM TokenId t where t.categName = :category") 
	String findIdByCategory(@Param("category") String category);

}

