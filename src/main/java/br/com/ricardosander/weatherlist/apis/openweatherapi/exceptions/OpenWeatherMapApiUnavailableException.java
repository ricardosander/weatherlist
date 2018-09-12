package br.com.ricardosander.weatherlist.apis.openweatherapi.exceptions;

import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;

public class OpenWeatherMapApiUnavailableException extends ApiUnavailableException {

  public OpenWeatherMapApiUnavailableException(String message) {
    super(message);
  }

}
