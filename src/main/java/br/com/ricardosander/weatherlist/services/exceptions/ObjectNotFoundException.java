package br.com.ricardosander.weatherlist.services.exceptions;

/**
 * Represents a object search and not found.
 */
public class ObjectNotFoundException extends RuntimeException {

  /**
   * @param message Exception message.
   */
  public ObjectNotFoundException(String message) {
    super(message);
  }

}