package br.com.ricardosander.weatherlist.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {

  public ObjectNotFoundException(String message) {
    super(message);
  }

}