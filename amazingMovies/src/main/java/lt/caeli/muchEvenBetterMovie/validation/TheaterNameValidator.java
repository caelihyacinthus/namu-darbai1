package lt.caeli.muchEvenBetterMovie.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TheaterNameValidator implements ConstraintValidator<TheaterName, String> {
  @Override
  public boolean isValid(String theaterName, ConstraintValidatorContext constraintValidatorContext) {
    return theaterName != null && theaterName.matches("[A-Z][A-Za-z| ]+");
  }
}
