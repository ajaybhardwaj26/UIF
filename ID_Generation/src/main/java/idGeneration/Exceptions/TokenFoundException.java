package idGeneration.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class TokenFoundException extends Exception {

	public TokenFoundException(String message) {
		super(message);
	}
	
	
	
}
