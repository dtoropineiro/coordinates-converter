package org.dario.coordinatesconverter.utils;

import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  public static final String WGS_84 = "WGS84";
  public static final String NAD_83 = "NAD83";
  public static final String GRS_80 = "GRS80";
  public static final String WGS_72 = "WGS72";
  public static final List<String> datums = List.of(WGS_84, NAD_83, GRS_80, WGS_72);
}
