package br.com.ricardosander.weatherlist.entities;

/**
 * Represents weather information.
 */
public class Weather {

  /**
   * Diferrence between Kelvin temperature and Celcius temperature.
   */
  private static final int CONVERT_KELVIN_TO_CELCIUS = 273;

  /**
   * Temperature, in Celcius.
   */
  private final double temperature;

  /**
   * @param temperature Temperature, in Celcius.
   */
  private Weather(double temperature) {
    this.temperature = temperature;
  }

  /**
   * Creates a Weather instance base on a temperature, in Kelvin.
   * @param temperatureInKelvin Temperature, in Kelvin.
   * @return
   */
  public static Weather fromKelvin(double temperatureInKelvin) {
    return new Weather(temperatureInKelvin - CONVERT_KELVIN_TO_CELCIUS);
  }

  /**
   * Creates a Weather instance base on a temperature, in Celcius.
   * @param temperatureInCelcius Temperature, in Celcius.
   * @return
   */
  public static Weather fromCelcius(double temperatureInCelcius) {
    return new Weather(temperatureInCelcius);
  }

  public double getTemperature() {
    return temperature;
  }

  @Override
  public String toString() {
    return String.valueOf(temperature);
  }

}
