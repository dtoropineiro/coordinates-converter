package org.dario.coordinatesconverter.service;

import org.dario.coordinatesconverter.domain.DecimalDegreesResponse;
import org.dario.coordinatesconverter.domain.UtmToDecimalDegreesRequest;

public interface CoordinatesConverterService {

  DecimalDegreesResponse utmToDecimalDegrees(UtmToDecimalDegreesRequest request);
}
