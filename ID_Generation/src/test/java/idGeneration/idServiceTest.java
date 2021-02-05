package idGeneration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import idGeneration.Exceptions.TokenNotFoundException;

@ExtendWith(MockitoExtension.class)
class iServiceTest1 {

	@Mock 
	private IdRepository idRepository; //creating a mock object here
	
	@InjectMocks 
	private IdService idService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void getUserTest() throws TokenNotFoundException {
		
		Optional<TokenId> tokid = Optional.ofNullable(new TokenId("t1", "employee", "emp-1"));
		
		//idRepository is mocked here to say whenever findById("..") is called on this repository with any string passed in, it will always return the tokid object with the above hard-coded values in it. 
		when(idRepository.findById(anyString())).thenReturn(tokid); 
		
		TokenId tokid2 = idService.getUser("t2"); //calls findById on repository and because of the above line, tokid2 will contain data for t1 (not for t2).
		
		assertThat(idService.getUser("t2")).isEqualTo(tokid.get());
//		assertNotNull(tokid2);
//		assertEquals("employee", tokid2.getCategName());
//		assertEquals("t1", tokid2.getTokenId());
//		assertEquals("emp-1", tokid2.getIdUniq());
//				
	}

	@Test
	void saveUserTest() {
		
		Optional<TokenId> tokid = Optional.ofNullable(new TokenId("t1", "employee", "emp-1"));
		
//		when(idRepository.save(tokid)).thenReturn(tokid);
//		
//		TokenId tokid2 = idService.saveUser(tokid);
//		
//		assertEquals(tokid, idService.saveUser(tokid));
					
	}

	
}
