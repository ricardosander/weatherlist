package br.com.ricardosander.weatherlist.dto;

class GeographicCoordinateValidator {

  static final double MAXIMUM_LATITUDE = 90.0;
  static final double MINIMUM_LATITUDE = -90.0;
  static final double MAXIMUM_LONGITUDE = 180.0;
  static final double MINIMUM_LONGITUDE = -180.0;

  boolean isLatitudeValid(GeographicCoordinate geoCoordinate) {
    return !(geoCoordinate.getLatitude() > MAXIMUM_LATITUDE) && !(geoCoordinate.getLatitude() <
        MINIMUM_LATITUDE);
  }

  boolean isLongitudeValid(GeographicCoordinate geoCoordinate) {
    return !(geoCoordinate.getLongitude() > MAXIMUM_LONGITUDE) && !(geoCoordinate.getLongitude()
        < MINIMUM_LONGITUDE);
  }

}
