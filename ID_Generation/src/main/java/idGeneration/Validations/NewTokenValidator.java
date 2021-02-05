package idGeneration.Validations;

import javax.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import idGeneration.IdRepository;
import idGeneration.IdService;


@Component
public class NewTokenValidator implements ConstraintValidator<NewToken, String> {

	@Autowired
	private IdService idService;

	@Autowired
	private IdRepository idRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//return value != null && !idService.isTokenAlreadyInUse(value); //or should I directly use below?
		return value != null && !idRepository.existsById(value);
	}
	
}
