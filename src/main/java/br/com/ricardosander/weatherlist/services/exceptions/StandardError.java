package br.com.ricardosander.weatherlist.services.exceptions;

import java.io.Serializable;

/**
 * Application error for response.
 */
public class StandardError implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   *
   */
  private int status;

  /**
   * Error name.
   */
  private String error;

  /**
   * Error message
   */
  private String message;

  /**
   * Path from the request.
   */
  private String path;

  /**
   * @param status Status code.
   * @param error Error name.
   * @param message Error message
   * @param path Path from the request.
   */
  StandardError(int status, String error, String message,
      String path) {
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
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