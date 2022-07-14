package org.dario.coordinatesconverter.domain;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.dario.coordinatesconverter.constraints.EligibleDatum;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class UtmToDecimalDegreesRequest {

  private Double east;
  private Double north;
  private String hemisphere;
  private Integer zone;

  @NotBlank(message = "Datum is mandatory")
  @EligibleDatum
  private String datum;
}
