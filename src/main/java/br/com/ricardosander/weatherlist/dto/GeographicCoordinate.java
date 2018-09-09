package br.com.ricardosander.weatherlist.dto;

import static br.com.ricardosander.weatherlist.dto.GeographicCoordinateValidator.MAXIMUM_LATITUDE;
import static br.com.ricardosander.weatherlist.dto.GeographicCoordinateValidator.MAXIMUM_LONGITUDE;
import static br.com.ricardosander.weatherlist.dto.GeographicCoordinateValidator.MINIMUM_LATITUDE;
import static br.com.ricardosander.weatherlist.dto.GeographicCoordinateValidator.MINIMUM_LONGITUDE;

import java.security.InvalidParameterException;
import java.util.Objects;

public class GeographicCoordinate {

  private final static GeographicCoordinateValidator validator =
      new GeographicCoordinateValidator();

  private static final String INVALID_LATITUDE_MESSAGE =
      "Latitude should be from " + MINIMUM_LATITUDE + " to " + MAXIMUM_LATITUDE;

  private static final String INVALID_LONGITUDE_MESSAGE =
      "Longitude should be from " + MINIMUM_LONGITUDE + " to " + MAXIMUM_LONGITUDE;

  private static final String INVALID_GEOGRAPHIC_COORDINATES =
      INVALID_LATITUDE_MESSAGE + " and " + INVALID_LONGITUDE_MESSAGE;

  private final double latitude;

  private final double longitude;

  private GeographicCoordinate(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public static GeographicCoordinate newInstance(double latitude, double longitude) {

    GeographicCoordinate geoCoordinate = new GeographicCoordinate(latitude, longitude);

    if (validator.isLatitudeValid(geoCoordinate) && validator.isLongitudeValid(geoCoordinate)) {
      return geoCoordinate;
    }

    if (validator.isLatitudeValid(geoCoordinate)) {
      throw new InvalidParameterException(INVALID_LONGITUDE_MESSAGE);
    }

    if (validator.isLongitudeValid(geoCoordinate)) {
      throw new InvalidParameterException(INVALID_LATITUDE_MESSAGE);
    }

    throw new InvalidParameterException(INVALID_GEOGRAPHIC_COORDINATES);
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  @Override
  public String toString() {
    return "Geographic coordinates : " + "latitude = " + latitude + ", longitude = " + longitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof GeographicCoordinate)) { return false; }
    GeographicCoordinate that = (GeographicCoordinate) o;
    return Double.compare(that.getLatitude(), getLatitude()) == 0 &&
        Double.compare(that.getLongitude(), getLongitude()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getLatitude(), getLongitude());
  }
}