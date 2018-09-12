package br.com.ricardosander.weatherlist.apis.openweatherapi;

import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;

class OpenWeatherMapApiUnavailableException extends ApiUnavailableException {

  OpenWeatherMapApiUnavailableException(String message) {
    super(message);
  }

}
