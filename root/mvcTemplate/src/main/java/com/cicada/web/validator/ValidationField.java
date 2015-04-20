package com.cicada.web.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Inherited
@Constraint(validatedBy = FieldValidator.class)
public @interface ValidationField {

	String message() default "Invalid {value} field name";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	ValidationFieldType value();

}
