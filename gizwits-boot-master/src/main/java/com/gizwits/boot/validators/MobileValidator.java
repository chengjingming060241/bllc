package com.gizwits.boot.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

/**
*@Author rongmc 
*@Date 2017/6/15 21:57
*
*/
public class MobileValidator implements ConstraintValidator<Mobile,String> {

    @Override
    public void initialize(Mobile constraintAnnotation) {
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(value))
            return true;
        if(value.matches("((\\+86)|(86))?1[3|4|5|6|7|8|9]\\d{9}")){
            return true;
        }
        return false;
    }
}