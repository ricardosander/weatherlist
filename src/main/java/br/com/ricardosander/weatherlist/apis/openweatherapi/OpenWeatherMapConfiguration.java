package br.com.ricardosander.weatherlist.apis.openweatherapi;

import br.com.ricardosander.weatherlist.apis.WeatherApiConfiguration;

/**
 * OpenWeatherMap API configuration.
 */
public class OpenWeatherMapConfiguration implements WeatherApiConfiguration {

  /**
   * OpenWeatherMap API key.
   */
  private final String key;

  /**
   * Cache of weather by cities in minutes.
   */
  private final int cityCacheTimeInMinutes;

  /**
   * Cache of weather by geo coordinates in minutes.
   */
  private final int geoCoordinatesCacheTimeInMinutes;

  /**
   * Size of the cache of weather by city.
   */
  private final int cityCacheSize;

  /**
   * Size of the cache of weather by geo coordinates.
   */
  private final int geoCoordinatesCacheSize;

  /**
   * @param key OpenWeatherMap API key.
   * @param cityCacheTimeInMinutes Cache of weather by cities in minutes.
   * @param geoCoordinatesCacheTimeInMinutes Cache of weather by geo coordinates in minutes.
   * @param cityCacheSize Size of the cache of weather by city.
   * @param geoCoordinatesCacheSize Size of the cache of weather by geo coordinates.
   */
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
