package br.com.ricardosander.weatherlist.entities;

public enum Category {

  PARTY,
  POP,
  ROCK,
  CLASSIC;

  static final double PARTY_TEMPERATURE_IN_CELCIUS = 30.0;
  static final double POP_TEMPERATURE_IN_CELCIUS = 15.0;
  static final double ROCK_TEMPERATURE_IN_CELCIUS = 10.0;

  public static Category getInstance(Weather weather) {

    if (weather.getTemperatureInCelcius() > PARTY_TEMPERATURE_IN_CELCIUS) {
      return PARTY;
    }

    if (weather.getTemperatureInCelcius() >= POP_TEMPERATURE_IN_CELCIUS) {
      return POP;
    }

    if (weather.getTemperatureInCelcius() >= ROCK_TEMPERATURE_IN_CELCIUS) {
      return ROCK;
    }

    return CLASSIC;
  }

}
