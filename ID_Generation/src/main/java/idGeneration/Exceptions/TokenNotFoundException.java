package idGeneration.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenNotFoundException extends Exception {

	public TokenNotFoundException(String message) {
		super(message);
	}
	
	
}
