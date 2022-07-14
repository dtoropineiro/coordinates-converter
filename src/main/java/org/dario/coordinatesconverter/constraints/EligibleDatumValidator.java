package org.dario.coordinatesconverter.constraints;

import static org.dario.coordinatesconverter.utils.Constants.datums;

import java.util.Locale;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EligibleDatumValidator implements ConstraintValidator<EligibleDatum, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return datums.contains(value.toUpperCase(Locale.ROOT));
  }
}
