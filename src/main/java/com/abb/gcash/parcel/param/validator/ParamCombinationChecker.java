package com.abb.gcash.parcel.param.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.abb.gcash.parcel.dto.DeliveryCostDto;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ParamCombinationChecker.Validator.class })
public @interface ParamCombinationChecker {
    String message() default "Fruit serial number is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ParamCombinationChecker, DeliveryCostDto> {

	@Override
	public boolean isValid(DeliveryCostDto arg0, ConstraintValidatorContext arg1) {
	    if (arg0.getWeight() == null) {
		return false;
	    }
	    if (arg0.getHeight() == null || arg0.getLength() == null || arg0.getWidth() == null) {
		return false;

	    }
	    return true;
	}

    }
}
