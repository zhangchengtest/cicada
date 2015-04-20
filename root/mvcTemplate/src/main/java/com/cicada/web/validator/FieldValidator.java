package com.cicada.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldValidator implements ConstraintValidator<ValidationField, Object> {

	private ValidationField field;

	@Override
	public void initialize(ValidationField constraintAnnotation) {
		this.field = constraintAnnotation;
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		// TODO: your validation logic here

		ValidationFieldType type = field.value();
		switch (type) {
		case A:
			return true;
		case B:
		case C:
		default:
			return false;
		}
	}
}
