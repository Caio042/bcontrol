package com.caiolima.bcontrol.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorValidator implements ConstraintValidator<Color, String> {

    private static final String REGEX = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";
    private static final Pattern pattern = Pattern.compile(REGEX);

    @Override
    public boolean isValid(String color, ConstraintValidatorContext context) {
        Matcher matcher = pattern.matcher(color);
        return matcher.matches();

    }
}
