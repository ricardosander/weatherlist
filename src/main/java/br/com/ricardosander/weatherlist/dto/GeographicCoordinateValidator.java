package br.com.ricardosander.weatherlist.dto;

class GeographicCoordinateValidator {

  boolean isLatitudeValid(GeographicCoordinate geoCoordinate) {
    return !(geoCoordinate.getLatitude() > 90.0) && !(geoCoordinate.getLatitude() < -90.0);
  }

  boolean isLongitudeValid(GeographicCoordinate geoCoordinate) {
    return !(geoCoordinate.getLongitude() > 180.0) && !(geoCoordinate.getLongitude() < -180.0);
  }

}
