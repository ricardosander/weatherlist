package br.com.ricardosander.weatherlist.services.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public class ResourceExceptionHandlerTest {

  private ResourceExceptionHandler resourceExceptionHandler;

  private HttpServletRequest request;

  @Before
  public void setUp() {
    resourceExceptionHandler = new ResourceExceptionHandler();
    request = mock(HttpServletRequest.class);
  }

  @Test
  public void objectNotFound() {

    String message = "I could not find your object";
    ObjectNotFoundException exception =
        new ObjectNotFoundException(message);

    ResponseEntity<StandardError> responseEntity =
        resourceExceptionHandler.handleException(exception, request);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
    assertEquals(message, responseEntity.getBody().getMessage());
    assertEquals(ResourceExceptionHandler.NOT_FOUND_ERROR, responseEntity.getBody().getError());
  }

  @Test
  public void invalidParameterException() {

    String message = "Invalid things";
    InvalidParameterException exception =
        new InvalidParameterException(message);

    ResponseEntity<StandardError> responseEntity =
        resourceExceptionHandler.handleException(exception, request);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseEntity.getBody().getStatus());
    assertEquals(message, responseEntity.getBody().getMessage());
    assertEquals(ResourceExceptionHandler.INVALID_PARAMETER_ERROR,
        responseEntity.getBody().getError());
  }
}