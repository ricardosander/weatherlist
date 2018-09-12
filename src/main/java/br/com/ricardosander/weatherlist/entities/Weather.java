package br.com.ricardosander.weatherlist.entities;

public class Weather {

  private static final int CONVERT_KELVIN_TO_CELCIUS = 273;
  private final double temperature;

  private Weather(double temperature) {
    this.temperature = temperature;
  }

  public static Weather fromKelvin(double temperatureInKelvin) {
    return new Weather(temperatureInKelvin - CONVERT_KELVIN_TO_CELCIUS);
  }

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
