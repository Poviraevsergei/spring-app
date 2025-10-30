package by.tms.annotation;

import by.tms.service.validation.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {AgeValidator.class})
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface CustomAge {
    String message() default "Возраст должен быть от 18 до 120";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
