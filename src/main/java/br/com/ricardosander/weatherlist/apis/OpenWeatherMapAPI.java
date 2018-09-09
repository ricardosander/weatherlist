package br.com.ricardosander.weatherlist.apis;

import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Weather;
import br.com.ricardosander.weatherlist.services.exceptions.ObjectNotFoundException;

public class OpenWeatherMapAPI implements WeatherAPI {

  private static final String VERY_HOT_CITY_NAME = "Natal";
  private static final String HOT_CITY_NAME = "Campinas";
  private static final String CHILLY_CITY_NAME = "Curitiba";
  private static final String FREEZING_CITY_NAME = "Porto Alegre";

  private static final double VERY_HOT_LATITUDE = 11.26;
  private static final double VERY_HOT_LONGITUDE = 13.47;
  private static final double HOT_LATITUDE = -15.68;
  private static final double HOT_LONGITUDE = 17.89;
  private static final double CHILLY_LATITUDE = 20.90;
  private static final double CHILLY_LONGITUDE = -21.11;
  private static final double FREEZING_LATITUDE = -21.52;
  private static final double FREEZING_LONGITUDE = -21.43;

  private final GeographicCoordinate veryHotGeographicCoordinate =
      GeographicCoordinate.newInstance(VERY_HOT_LATITUDE, VERY_HOT_LONGITUDE);

  private final GeographicCoordinate hotGeographicCoordinate =
      GeographicCoordinate.newInstance(HOT_LATITUDE, HOT_LONGITUDE);

  private final GeographicCoordinate chillyGeographicCoordinate =
      GeographicCoordinate.newInstance(CHILLY_LATITUDE, CHILLY_LONGITUDE);

  private final GeographicCoordinate freezingGeographicCoordinate =
      GeographicCoordinate.newInstance(FREEZING_LATITUDE, FREEZING_LONGITUDE);

  @Override
  public Weather findWeatherByCityName(String cityName) {

    if (cityName.equals(VERY_HOT_CITY_NAME)) {

      double veryHotTemperatureInCelcius = 31.0;
      return new Weather(veryHotTemperatureInCelcius);
    }

    if (cityName.equals(HOT_CITY_NAME)) {
      double hotTemperatureInCelcius = 28.0;
      return new Weather(hotTemperatureInCelcius);
    }

    if (cityName.equals(CHILLY_CITY_NAME)) {
      double chillyTemperatureInCelcius = 12.0;
      return new Weather(chillyTemperatureInCelcius);
    }

    if (cityName.equals(FREEZING_CITY_NAME)) {
      double freezingTemperatureInCelcius = 6.0;
      return new Weather(freezingTemperatureInCelcius);
    }

    throw new ObjectNotFoundException("City with city name " + cityName + " not found.");
  }

  @Override
  public Weather findWeather(GeographicCoordinate geographicCoordinate) {

    if (geographicCoordinate.equals(veryHotGeographicCoordinate)) {
      double veryHotTemperatureInCelcius = 31.0;
      return new Weather(veryHotTemperatureInCelcius);
    }

    if (geographicCoordinate.equals(hotGeographicCoordinate)) {
      double hotTemperatureInCelcius = 28.0;
      return new Weather(hotTemperatureInCelcius);
    }

    if (geographicCoordinate.equals(chillyGeographicCoordinate)) {
      double chillyTemperatureInCelcius = 12.0;
      return new Weather(chillyTemperatureInCelcius);
    }

    if (geographicCoordinate.equals(freezingGeographicCoordinate)) {
      double freezingTemperatureInCelcius = 6.0;
      return new Weather(freezingTemperatureInCelcius);
    }

    throw new ObjectNotFoundException(geographicCoordinate.toString() + " not found.");
  }

}
