package org.dario.coordinatesconverter.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class DatumDto {
  private SemiAxisDto semiAxis;
  private Double majorEccentricity;
  private Double minorEccentricity;
  private Double powMinorEccentricity;
  private Double polarRadiusCurvature;
}
