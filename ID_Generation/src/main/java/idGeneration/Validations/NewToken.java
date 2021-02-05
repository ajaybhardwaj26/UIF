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
@Constraint(validatedBy = NewTokenValidator.class)

public @interface NewToken {

	public String message() default "Pass a new token please, there is already a user with this token!";
	
	public Class<?>[] groups() default {};
	
	public Class<? extends Payload>[] payload() default{};
	
}
