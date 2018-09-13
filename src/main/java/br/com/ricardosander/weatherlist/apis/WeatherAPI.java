package br.com.ricardosander.weatherlist.apis;

import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;
import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Weather;

/**
 * API responsible for retrieving weather information based on a location.
 */
public interface WeatherAPI {

  /**
   * Retrieves weather information given a city name.
   * @param cityName city name for API search.
   * @return
   * @throws ApiUnavailableException
   */
  Weather findWeatherByCityName(String cityName) throws ApiUnavailableException;

  /**
   * Retrieves weather information given a geo coordinates.
   * @param geographicCoordinate geo coordinates for API search.
   * @return
   * @throws ApiUnavailableException
   */
  Weather findWeather(GeographicCoordinate geographicCoordinate) throws ApiUnavailableException;

}
