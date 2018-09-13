package br.com.ricardosander.weatherlist.services.exceptions;

/**
 * Represents an invalid parameter.
 */
public class InvalidParameterException extends RuntimeException {

  /**
   * @param message Exception message.
   */
  public InvalidParameterException(String message) {
    super(message);
  }

}
