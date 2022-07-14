package org.dario.coordinatesconverter.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EligibleDatumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EligibleDatum {

  String message() default "The Datum is invalid.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
