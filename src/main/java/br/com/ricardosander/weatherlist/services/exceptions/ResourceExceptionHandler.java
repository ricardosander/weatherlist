package br.com.ricardosander.weatherlist.services.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(ResourceExceptionHandler.class);

  static final String INVALID_PARAMETER_ERROR = "Invalid Parameter";
  static final String NOT_FOUND_ERROR = "Not found";

  private static final String UNEXPECTED_ERROR = "UNEXPECTED ERROR";

  private static final String INVALID_PARAMETER_MESSAGE = "Invalid value for parameter '%s'.";
  private static final String MISSING_PARAMETER_MESSAGE = "Parameter '%s' is required.";
  private static final String TRY_AGAIN_LATER = "Please, try again later.";

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> handleException(ObjectNotFoundException exception,
      HttpServletRequest request) {

    HttpStatus status = HttpStatus.NOT_FOUND;
    StandardError standardError =
        new StandardError(System.currentTimeMillis(), status.value(), NOT_FOUND_ERROR,
            exception.getMessage(), request.getRequestURI());

    return ResponseEntity.status(status).body(standardError);
  }

  @ExceptionHandler(InvalidParameterException.class)
  public ResponseEntity<StandardError> handleException(InvalidParameterException exception,
      HttpServletRequest request) {

    logger.info(exception.getMessage());

    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    StandardError standardError =
        new StandardError(System.currentTimeMillis(), status.value(), INVALID_PARAMETER_ERROR,
            exception.getMessage(), request.getRequestURI());

    return ResponseEntity.status(status).body(standardError);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<StandardError> handleException(
      MissingServletRequestParameterException exception, HttpServletRequest request) {

    HttpStatus status = HttpStatus.BAD_REQUEST;
    StandardError standardError =
        new StandardError(System.currentTimeMillis(), status.value(), status.name(),
            String.format(MISSING_PARAMETER_MESSAGE, exception.getParameterName()),
            request.getRequestURI());

    return ResponseEntity.status(status).body(standardError);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<StandardError> handleException(
      MethodArgumentTypeMismatchException exception, HttpServletRequest request) {

    HttpStatus status = HttpStatus.BAD_REQUEST;
    StandardError standardError =
        new StandardError(System.currentTimeMillis(), status.value(), status.name(),
            String.format(INVALID_PARAMETER_MESSAGE, exception.getName()), request.getRequestURI());

    return ResponseEntity.status(status).body(standardError);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<StandardError> handleException(Exception exception,
      HttpServletRequest request) {

    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    StandardError standardError =
        new StandardError(System.currentTimeMillis(), status.value(), UNEXPECTED_ERROR,
            TRY_AGAIN_LATER + exception.toString(), request.getRequestURI());

    return ResponseEntity.status(status).body(standardError);
  }

}