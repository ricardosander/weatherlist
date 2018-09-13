package br.com.ricardosander.weatherlist.apis.exceptions;

/**
 * Identifies an API error.
 */
public class ApiUnavailableException extends Exception {

  /**
   * @param message Exception message
   */
  public ApiUnavailableException(String message) {
    super(message);
  }

}
