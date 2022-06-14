package com.caiolima.bcontrol.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ColorValidator.class)
public @interface Color {

    String message() default "Formato de cor inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
