package br.com.ricardosander.weatherlist.apis.openweatherapi;

import br.com.ricardosander.weatherlist.apis.WeatherApiConfiguration;

public class OpenWeatherMapConfiguration implements WeatherApiConfiguration {

  private final String key;

  private final int cityCacheTimeInMinutes;

  private final int geoCoordinatesCacheTimeInMinutes;

  private final int cityCacheSize;

  private final int geoCoordinatesCacheSize;

  public OpenWeatherMapConfiguration(String key, int cityCacheTimeInMinutes,
      int geoCoordinatesCacheTimeInMinutes, int cityCacheSize, int geoCoordinatesCacheSize) {
    this.key = key;
    this.cityCacheTimeInMinutes = cityCacheTimeInMinutes;
    this.geoCoordinatesCacheTimeInMinutes = geoCoordinatesCacheTimeInMinutes;
    this.cityCacheSize = cityCacheSize;
    this.geoCoordinatesCacheSize = geoCoordinatesCacheSize;
  }

  String getKey() {
    return key;
  }

  int getCityCacheTimeInMinutes() {
    return cityCacheTimeInMinutes;
  }

  int getGeoCoordinatesCacheTimeInMinutes() {
    return geoCoordinatesCacheTimeInMinutes;
  }

  int getCityCacheSize() {
    return cityCacheSize;
  }

  int getGeoCoordinatesCacheSize() {
    return geoCoordinatesCacheSize;
  }

}
