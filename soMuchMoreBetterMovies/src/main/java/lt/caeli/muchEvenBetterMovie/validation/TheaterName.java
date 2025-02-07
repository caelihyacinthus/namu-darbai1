package lt.caeli.muchEvenBetterMovie.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TheaterNameValidator.class)
public @interface TheaterName {
  String message() default "Name can't be null, name has to start with capital letter";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
