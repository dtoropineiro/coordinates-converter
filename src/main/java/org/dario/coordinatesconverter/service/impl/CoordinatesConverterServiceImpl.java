package org.dario.coordinatesconverter.service.impl;

import static java.lang.Math.PI;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.dario.coordinatesconverter.config.DatumConfiguration;
import org.dario.coordinatesconverter.domain.DatumDto;
import org.dario.coordinatesconverter.domain.DecimalDegreesResponse;
import org.dario.coordinatesconverter.domain.UtmToDecimalDegreesRequest;
import org.dario.coordinatesconverter.exceptions.BadRequestException;
import org.dario.coordinatesconverter.service.CoordinatesConverterService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoordinatesConverterServiceImpl implements CoordinatesConverterService {

  public static final String S = "S";
  public static final String N = "N";
  public static final float SCALE_ON_CENTRAL_MERIDIAN = 0.9996f;

  private final DatumConfiguration datumConfiguration;

  @Override
  public DecimalDegreesResponse utmToDecimalDegrees(UtmToDecimalDegreesRequest request) {
    Double y = calculateY(request.getNorth(), request.getHemisphere());
    Double phi = calculatePhi(y);
    Integer centralMeridian = calculateCentralMeridian(request.getZone());
    DatumDto datum = buildDatumDto(request);
    Double ni = calculateNi(datum, phi, y);
    Double a = calculateA(request.getEast(), ni);
    Double a1 = calculateA1(phi);
    Double a2 = calculateA2(a1, phi);
    Double j2 = calculateJ2(a1, phi);
    Double j4 = calculateJ4(a2, j2);
    Double j6 = calculateJ6(a2, phi, j4);
    Double alpha = calculateAlpha(datum.getPowMinorEccentricity());
    Double beta = calculateBeta(alpha);
    Double gamma = calculateGamma(alpha);
    Double bPhi =
        calculateBPhi(datum.getPolarRadiusCurvature(), phi, j2, j4, j6, alpha, beta, gamma);
    Double b = calculateB(ni, y, bPhi);
    Double zeta = calculateZeta(datum.getPowMinorEccentricity(), phi, a);
    Double xi = calculateXi(a, zeta);
    Double eta = calculateEta(b, zeta, phi);
    Double hXi = calculateHXi(xi);
    Double deltaLambda = calculateDeltaLambda(eta, hXi);
    Double tau = calculateTau(eta, deltaLambda);
    Double phiRadian = calculatePhiRadian(phi, datum.getPowMinorEccentricity(), tau);
    Double decimalDegreesLatitude = calculateDecimalDegreesLatitude(phiRadian);
    Double decimalDegreesLongitude = calculateDecimalDegreesLongitude(centralMeridian, deltaLambda);

    DecimalDegreesResponse decimalDegreesResponse =
        DecimalDegreesResponse.builder()
            .latitudeDegrees(decimalDegreesLatitude)
            .longitudeDegrees(decimalDegreesLongitude)
            .build();
    return decimalDegreesResponse;
  }

  private Double calculateY(Double north, String hemisphere) {
    if (hemisphere.equalsIgnoreCase(S)) {
      return north - 10000000;
    } else if (hemisphere.equalsIgnoreCase(N)) {
      return north;
    } else {
      throw new BadRequestException(null, null, null);
    }
  }

  private Double calculatePhi(Double y) {
    Double phi = y / (6366197.724f * SCALE_ON_CENTRAL_MERIDIAN);
    return phi;
  }

  private Double calculateNi(DatumDto datum, Double phi, Double y) {
    return (datum.getPolarRadiusCurvature()
            / Math.sqrt(1 + datum.getPowMinorEccentricity() * (Math.cos(phi) * Math.cos(phi))))
        * SCALE_ON_CENTRAL_MERIDIAN;
  }

  private Integer calculateCentralMeridian(Integer zone) {
    return 6 * zone - 183;
  }

  private Double calculateA(Double east, Double ni) {
    return (east - 500000) / ni;
  }

  private Double calculateA1(Double phi) {
    return Math.sin(2 * phi);
  }

  private Double calculateA2(Double a1, Double phi) {
    return a1 * Math.pow(Math.cos(phi), 2);
  }

  private Double calculateJ2(Double a1, Double phi) {
    return phi + (a1 / 2);
  }

  private Double calculateJ4(Double a2, Double j2) {
    return ((3 * j2) + a2) / 4;
  }

  private Double calculateJ6(Double a2, Double phi, Double j4) {
    return (5 * j4 + a2 * Math.pow(Math.cos(phi), 2)) / 3;
  }

  private Double calculateAlpha(Double powMinorEccentricity) {
    return (0.75) * powMinorEccentricity;
  }

  private Double calculateBeta(Double alpha) {
    return (5 / 3) * Math.pow(alpha, 2);
  }

  private Double calculateGamma(Double alpha) {
    return (35 / 27) * Math.pow(alpha, 3);
  }

  private Double calculateBPhi(
      Double polarRadiusCurvature,
      Double phi,
      Double j2,
      Double j4,
      Double j6,
      Double alpha,
      Double beta,
      Double gamma) {
    return SCALE_ON_CENTRAL_MERIDIAN
        * polarRadiusCurvature
        * (phi - (alpha * j2) + (beta * j4) - (gamma * j6));
  }

  private Double calculateB(Double ni, Double y, Double bPhi) {
    return (y - bPhi) / ni;
  }

  private Double calculateZeta(Double powMinorEccentricity, Double phi, Double a) {
    return (powMinorEccentricity * a * a / 2) * Math.pow(Math.cos(phi), 2);
  }

  private Double calculateXi(Double a, Double zeta) {
    return a * (1 - zeta / 3);
  }

  private Double calculateEta(Double b, Double zeta, Double phi) {
    return b * (1 - zeta) + phi;
  }

  private Double calculateHXi(Double xi) {
    return (Math.exp(xi) - Math.exp(-xi)) / 2;
  }

  private Double calculateDeltaLambda(Double eta, Double hXi) {
    return Math.atan(hXi / (Math.cos(eta)));
  }

  private Double calculateTau(Double eta, Double deltaLambda) {
    return Math.atan((Math.cos(deltaLambda) * Math.tan(eta)));
  }

  private Double calculatePhiRadian(Double phi, Double powMinorEccentricity, Double tau) {
    return phi
        + (1
                + powMinorEccentricity
                    * (Math.pow(Math.cos(phi), 2)
                        - (3 / 2) * Math.sin(phi) * Math.cos((phi) * (tau - phi))))
            * (tau - phi);
  }

  private Double calculateDecimalDegreesLatitude(Double phiRadian) {
    return (phiRadian / PI) * 180;
  }

  private Double calculateDecimalDegreesLongitude(Integer centralMeridian, Double deltaLambda) {
    return (deltaLambda / PI) * 180 + centralMeridian;
  }

  private DatumDto buildDatumDto(UtmToDecimalDegreesRequest request) {
    String datum = request.getDatum();
    Map<String, DatumConfiguration.SemiAxis> datumMap = datumConfiguration.datumMap();
    Double majorSemiAxis = datumMap.get(datum).getMajorSemiAxis();
    Double minorSemiAxis = datumMap.get(datum).getMinorSemiAxis();
    Double majorEccentricity =
        Math.sqrt(1 - (minorSemiAxis / majorSemiAxis) * (minorSemiAxis / majorSemiAxis));
    Double minorEccentricity =
        Math.sqrt((majorSemiAxis / minorSemiAxis) * (majorSemiAxis / minorSemiAxis) - 1);
    Double powMinorEccentricity = minorEccentricity * minorEccentricity;
    Double polarRadiusCurvature = (majorSemiAxis * majorSemiAxis) / minorSemiAxis;
    return DatumDto.builder()
        .minorEccentricity(minorEccentricity)
        .majorEccentricity(majorEccentricity)
        .powMinorEccentricity(powMinorEccentricity)
        .polarRadiusCurvature(polarRadiusCurvature)
        .build();
  }
}
