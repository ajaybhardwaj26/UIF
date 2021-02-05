package idGeneration;

import org.springframework.data.jpa.repository.JpaRepository;

//Create Id Repository interface extending JPA Repository.
//There are built-in methods for CRUD operations in JpaRepository,writing any SQL query is not needed.

//Pattern - the class name related to the table "pattern"
public interface CategoryRepository extends JpaRepository<Pattern,String> {

}
