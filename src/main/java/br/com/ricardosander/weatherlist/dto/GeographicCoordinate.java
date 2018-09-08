package br.com.ricardosander.weatherlist.dto;

public class GeographicCoordinate {

  private final double latitude;

  private final double longitude;

  private GeographicCoordinate(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public static GeographicCoordinate newInstance(double latitude, double longitude) {
    return new GeographicCoordinate(latitude, longitude);
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

}