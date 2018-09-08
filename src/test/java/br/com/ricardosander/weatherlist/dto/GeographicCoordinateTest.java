package br.com.ricardosander.weatherlist.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.security.InvalidParameterException;

public class GeographicCoordinateTest {

  @Test
  public void newInstanceWithPositiveNumber() {

    double latitude = 90.00;
    double longitude = 80.29;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertNotNull(geoCoordinate);
    assertEquals(latitude, geoCoordinate.getLatitude(), 0.1);
    assertEquals(longitude, geoCoordinate.getLongitude(), 0.1);
  }

  @Test
  public void newInstanceWithNegativeLongitude() {

    double latitude = 45.234;
    double longitude = -100;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertNotNull(geoCoordinate);
    assertEquals(latitude, geoCoordinate.getLatitude(), 0.1);
    assertEquals(longitude, geoCoordinate.getLongitude(), 0.1);
  }

  @Test
  public void newInstanceWithNegativeLatitude() {

    double latitude = -30.23;
    double longitude = 170;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertNotNull(geoCoordinate);
    assertEquals(latitude, geoCoordinate.getLatitude(), 0.1);
    assertEquals(longitude, geoCoordinate.getLongitude(), 0.1);
  }

  @Test
  public void newInstanceWithNegativeNumber() {

    double latitude = -34.56;
    double longitude = -67.16;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertNotNull(geoCoordinate);
    assertEquals(latitude, geoCoordinate.getLatitude(), 0.1);
    assertEquals(longitude, geoCoordinate.getLongitude(), 0.1);
  }

  @Test(expected = InvalidParameterException.class)
  public void invalidInstanceSinceLatitudeIstTooHigh() {

    double latitude = 90.01;
    double longitude = 80.29;

    GeographicCoordinate.newInstance(latitude, longitude);
  }

  @Test(expected = InvalidParameterException.class)
  public void invalidInstanceSinceLatitudeIsTooLow() {

    double latitude = -90.01;
    double longitude = 80.29;

    GeographicCoordinate.newInstance(latitude, longitude);
  }

  @Test(expected = InvalidParameterException.class)
  public void invalidInstanceSinceLongitudeIstTooHigh() {

    double latitude = 80.0;
    double longitude = 180.01;

    GeographicCoordinate.newInstance(latitude, longitude);
  }

  @Test(expected = InvalidParameterException.class)
  public void invalidInstanceSinceLongitudeIsTooLow() {

    double latitude = 80.00;
    double longitude = -180.01;

    GeographicCoordinate.newInstance(latitude, longitude);
  }

}