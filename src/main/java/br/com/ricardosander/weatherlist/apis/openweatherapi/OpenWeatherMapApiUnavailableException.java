package br.com.ricardosander.weatherlist.apis.openweatherapi;

import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;

/**
 * Identifies an OpenWeatherMap API error.
 */
class OpenWeatherMapApiUnavailableException extends ApiUnavailableException {

  /**
   * @param message excepetion message.
   */
  OpenWeatherMapApiUnavailableException(String message) {
    super(message);
  }

}
