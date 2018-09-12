package br.com.ricardosander.weatherlist.services.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {

  private static final long serialVersionUID = 1L;

  private int status;

  private String error;

  private String message;

  private String path;

  StandardError(int status, String error, String message,
      String path) {
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }

}