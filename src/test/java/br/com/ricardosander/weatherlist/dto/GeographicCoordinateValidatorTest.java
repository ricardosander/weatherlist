package br.com.ricardosander.weatherlist.dto;

import static br.com.ricardosander.weatherlist.dto.GeographicCoordinateValidator.MAXIMUM_LATITUDE;
import static br.com.ricardosander.weatherlist.dto.GeographicCoordinateValidator.MAXIMUM_LONGITUDE;
import static br.com.ricardosander.weatherlist.dto.GeographicCoordinateValidator.MINIMUM_LATITUDE;
import static br.com.ricardosander.weatherlist.dto.GeographicCoordinateValidator.MINIMUM_LONGITUDE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import br.com.ricardosander.weatherlist.services.exceptions.InvalidParameterException;
import org.junit.Before;
import org.junit.Test;

public class GeographicCoordinateValidatorTest {

  private GeographicCoordinateValidator geographicCoordinateValidator;

  @Before
  public void setUp() {
    geographicCoordinateValidator = new GeographicCoordinateValidator();
  }

  @Test
  public void testWithZeros() {

    double latitude = 0.0;
    double longitude = 0.0;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertTrue(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertTrue(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test
  public void testWithValidLatitudeAndLongitude() {

    double latitude = 45.0;
    double longitude = 45.0;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertTrue(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertTrue(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test
  public void testWithPositiveLimitValidLatitudeAndLongitude() {

    double latitude = MAXIMUM_LATITUDE;
    double longitude = MAXIMUM_LONGITUDE;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertTrue(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertTrue(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test
  public void testWithNegativeLimitValidLatitudeAndLongitude() {

    double latitude = MINIMUM_LATITUDE;
    double longitude = MINIMUM_LONGITUDE;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertTrue(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertTrue(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test
  public void testWithLimitValuesNegativeLatitude() {

    double latitude = MINIMUM_LATITUDE;
    double longitude = MAXIMUM_LONGITUDE;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertTrue(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertTrue(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test
  public void testWithLimitValuesNegativeLongitude() {

    double latitude = MAXIMUM_LATITUDE;
    double longitude = MINIMUM_LONGITUDE;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertTrue(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertTrue(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test(expected = InvalidParameterException.class)
  public void testWithInvalidPositiveLatitude() {

    double latitude = 91.0;
    double longitude = 45.0;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertFalse(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertTrue(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test(expected = InvalidParameterException.class)
  public void testWithInvalidPositiveLongitude() {

    double latitude = 45.0;
    double longitude = 181.0;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertTrue(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertFalse(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test(expected = InvalidParameterException.class)
  public void testWithInvalidPositiveLatitudeAlmostValid() {

    double latitude = 90.009;
    double longitude = 45.0;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertFalse(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertTrue(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test(expected = InvalidParameterException.class)
  public void testWithInvalidPositiveLongitudeAlmostValid() {

    double latitude = 45.0;
    double longitude = 180.009;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertTrue(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertFalse(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test(expected = InvalidParameterException.class)
  public void testWithInvalidNegativeLatitude() {

    double latitude = -91.0;
    double longitude = 45.0;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertFalse(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertTrue(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test(expected = InvalidParameterException.class)
  public void testWithInvalidNegativeLongitude() {

    double latitude = 45.0;
    double longitude = -181.0;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertTrue(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertFalse(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test(expected = InvalidParameterException.class)
  public void testWithInvalidNegativeLatitudeAlmostValid() {

    double latitude = -90.009;
    double longitude = 45.0;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertFalse(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertTrue(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

  @Test(expected = InvalidParameterException.class)
  public void testWithInvalidNegativeLongitudeAlmostValid() {

    double latitude = 45;
    double longitude = -180.009;

    GeographicCoordinate geoCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    assertTrue(geographicCoordinateValidator.isLatitudeValid(geoCoordinate));
    assertFalse(geographicCoordinateValidator.isLongitudeValid(geoCoordinate));
  }

}