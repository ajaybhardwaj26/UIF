package idGeneration.Exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(TokenFoundException.class)
	public ResponseEntity<?> tokenFoundException(TokenFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false)); //Create an error response object and return it with a specific Http Status.
		return new ResponseEntity<>(errorDetails,HttpStatus.FOUND); 
	}
	
	@ExceptionHandler(CategNotFoundException.class)
	public ResponseEntity<?> categNotFoundException(CategNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false)); //Create an error response object and return it with a specific Http Status.
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND); 
	}
	
	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<?> tokenNotFoundException(TokenNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false)); //Create an error response object and return it with a specific Http Status.
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND); 
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
