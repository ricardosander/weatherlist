package br.com.ricardosander.weatherlist.entities;

/**
 * Categories used on Playlist search.
 */
public enum Category {

  PARTY,
  POP,
  ROCK,
  CLASSICAL;

  static final double PARTY_TEMPERATURE_IN_CELCIUS = 30.0;
  static final double POP_TEMPERATURE_IN_CELCIUS = 15.0;
  static final double ROCK_TEMPERATURE_IN_CELCIUS = 10.0;

  /**
   * Return a Category give on the Weather.
   * @param weather weather.
   * @return
   */
  public static Category getInstance(Weather weather) {

    if (weather.getTemperature() > PARTY_TEMPERATURE_IN_CELCIUS) {
      return PARTY;
    }

    if (weather.getTemperature() >= POP_TEMPERATURE_IN_CELCIUS) {
      return POP;
    }

    if (weather.getTemperature() >= ROCK_TEMPERATURE_IN_CELCIUS) {
      return ROCK;
    }

    return CLASSICAL;
  }

  public String getLowerCase() {
    return this.toString().toLowerCase();
  }

}
