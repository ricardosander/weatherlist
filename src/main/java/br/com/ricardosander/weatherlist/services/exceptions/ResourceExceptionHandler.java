package br.com.ricardosander.weatherlist.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

  static final String INVALID_PARAMETER_ERROR = "Invalid Parameter";
  static final String NOT_FOUND_ERROR = "Not found";

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception,
      HttpServletRequest request) {


    HttpStatus status = HttpStatus.NOT_FOUND;
    StandardError standardError =
        new StandardError(System.currentTimeMillis(), status.value(), NOT_FOUND_ERROR,
            exception.getMessage(), request.getRequestURI());

    return ResponseEntity.status(status).body(standardError);
  }

  @ExceptionHandler(InvalidParameterException.class)
  public ResponseEntity<StandardError> invalidParameterException(
      InvalidParameterException exception,
      HttpServletRequest request) {


    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    StandardError standardError =
        new StandardError(System.currentTimeMillis(), status.value(), INVALID_PARAMETER_ERROR,
            exception.getMessage(), request.getRequestURI());

    return ResponseEntity.status(status).body(standardError);
  }

}