package org.dario.coordinateconverter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.dario.coordinatesconverter.config.DatumConfiguration;
import org.dario.coordinatesconverter.domain.DecimalDegreesResponse;
import org.dario.coordinatesconverter.domain.UtmToDecimalDegreesRequest;
import org.dario.coordinatesconverter.service.impl.CoordinatesConverterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CoordinatesConverterServiceTest {

  @InjectMocks CoordinatesConverterServiceImpl coordinatesConverterService;

  @Mock DatumConfiguration datumConfiguration;

  @Test
  void shouldCalculateWhenValidUtmToDecimalDegreesRequestInSouthHemisphere() {

    when(datumConfiguration.datumMap()).thenReturn(buildDatumMap());
    DecimalDegreesResponse expected = buildDecimalDegreesResponse();
    DecimalDegreesResponse result =
        coordinatesConverterService.utmToDecimalDegrees(buildUtmToDecimalDegreesRequest("S"));
    assertEquals(expected, result);
  }

  @Test
  void shouldCalculateWhenValidUtmToDecimalDegreesRequestInNorthHemisphere() {

    when(datumConfiguration.datumMap()).thenReturn(buildDatumMap());
    DecimalDegreesResponse expected = buildNorthDecimalDegreesResponse();
    DecimalDegreesResponse result =
        coordinatesConverterService.utmToDecimalDegrees(buildUtmToDecimalDegreesRequest("N"));
    assertEquals(expected, result);
  }

  private UtmToDecimalDegreesRequest buildUtmToDecimalDegreesRequest(String hemisphere) {
    return UtmToDecimalDegreesRequest.builder()
        .datum("WGS84")
        .east(391136.0)
        .north(1326751.0)
        .hemisphere(hemisphere)
        .zone(20)
        .build();
  }

  private DecimalDegreesResponse buildDecimalDegreesResponse() {
    return DecimalDegreesResponse.builder()
        .latitudeDegrees(-78.09506175676808)
        .longitudeDegrees(-67.73223543888506)
        .build();
  }

  private DecimalDegreesResponse buildNorthDecimalDegreesResponse() {
    return DecimalDegreesResponse.builder()
        .latitudeDegrees(12.0003200676493)
        .longitudeDegrees(-63.99999987092365)
        .build();
  }

  private Map<String, DatumConfiguration.SemiAxis> buildDatumMap() {
    DatumConfiguration.SemiAxis datum = new DatumConfiguration.SemiAxis();
    datum.setMajorSemiAxis(6378137.0);
    datum.setMinorSemiAxis(6356752.31424518);
    return Map.of("WGS84", datum);
  }
}
