package br.com.ricardosander.weatherlist.apis;

import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;
import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Weather;

public interface WeatherAPI {

  Weather findWeatherByCityName(String cityName) throws ApiUnavailableException;

  Weather findWeather(GeographicCoordinate geographicCoordinate) throws ApiUnavailableException;

}
