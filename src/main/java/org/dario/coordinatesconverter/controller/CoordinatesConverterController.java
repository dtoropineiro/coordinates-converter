package org.dario.coordinatesconverter.controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.dario.coordinatesconverter.domain.DecimalDegreesResponse;
import org.dario.coordinatesconverter.domain.UtmToDecimalDegreesRequest;
import org.dario.coordinatesconverter.service.CoordinatesConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/converter")
@AllArgsConstructor
public class CoordinatesConverterController {

  private final CoordinatesConverterService coordinatesConverterService;

  @PostMapping("/utm-to-decimal-degrees")
  public ResponseEntity<DecimalDegreesResponse> utmToDecimalDegrees(
      @Valid @RequestBody UtmToDecimalDegreesRequest request) {

    return ResponseEntity.ok(coordinatesConverterService.utmToDecimalDegrees(request));
  }
}
