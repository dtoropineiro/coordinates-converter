package org.dario.coordinateconverter.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;
import org.dario.coordinatesconverter.constraints.EligibleDatumValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EligibleDatumValidatorTest {

  @InjectMocks EligibleDatumValidator eligibleDatumValidator;

  @Mock ConstraintValidatorContext constraintValidatorContext;

  @Test
  void shouldReturnTrueWhenEligibleDatumIsGiven() {
    Boolean result = eligibleDatumValidator.isValid("WGS84", constraintValidatorContext);
    assertTrue(result);
  }

  @Test
  void shouldReturnFalseWhenNonEligibleDatumIsGiven() {
    Boolean result = eligibleDatumValidator.isValid("WGS90", constraintValidatorContext);
    assertFalse(result);
  }
}
