package idGeneration.Validations;

import java.lang.annotation.*;
import java.lang.annotation.*;
import javax.validation.*;

import idGeneration.TokenId.PostValidation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ExistingTokenValidator.class)

public @interface ExistingToken {

	public String message() default "The token must be an existing one!";
	
	public Class<?>[] groups() default {};
	
	public Class<? extends Payload>[] payload() default{};
	
}
