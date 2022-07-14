package org.dario.coordinateconverter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.dario.coordinatesconverter.controller.CoordinatesConverterController;
import org.dario.coordinatesconverter.domain.DecimalDegreesResponse;
import org.dario.coordinatesconverter.domain.UtmToDecimalDegreesRequest;
import org.dario.coordinatesconverter.service.impl.CoordinatesConverterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CoordinatesConverterControllerTest {
  @InjectMocks CoordinatesConverterController coordinatesConverterController;

  @Mock CoordinatesConverterServiceImpl coordinatesConverterService;

  @Test
  void should() {
    when(coordinatesConverterService.utmToDecimalDegrees(buildUtmToDecimalDegreesRequest()))
        .thenReturn(buildDecimalDegreesResponse());
    DecimalDegreesResponse expected = buildDecimalDegreesResponse();
    DecimalDegreesResponse response =
        coordinatesConverterController
            .utmToDecimalDegrees(buildUtmToDecimalDegreesRequest())
            .getBody();

    assertEquals(expected, response);
  }

  private UtmToDecimalDegreesRequest buildUtmToDecimalDegreesRequest() {
    return UtmToDecimalDegreesRequest.builder().build();
  }

  private DecimalDegreesResponse buildDecimalDegreesResponse() {
    return DecimalDegreesResponse.builder().build();
  }
}
