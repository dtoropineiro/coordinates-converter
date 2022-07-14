package org.dario.coordinatesconverter.config;

import java.util.Map;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "map")
@Getter
@Setter
public class DatumConfiguration {

  private Map<String, SemiAxis> datum;

  @Bean
  public Map<String, SemiAxis> datumMap() {
    return datum;
  }

  @Getter
  @Setter
  public static class SemiAxis {
    private Double majorSemiAxis;
    private Double minorSemiAxis;
    private Double polarFlattening;
  }
}
