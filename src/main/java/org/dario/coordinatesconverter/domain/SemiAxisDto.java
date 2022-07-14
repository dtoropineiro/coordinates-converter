package org.dario.coordinatesconverter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class SemiAxisDto {
  private Double majorSemiAxis;
  private Double minorSemiAxis;
}
