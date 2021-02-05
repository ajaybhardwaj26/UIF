package idGeneration.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategNotFoundException extends Exception{
	public CategNotFoundException(String message) {
		super(message);
	}

}