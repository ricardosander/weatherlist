package br.com.ricardosander.weatherlist.entities;

public class Weather {

  private final double temperatureInCelcius;

  public Weather(double temperatureInCelcius) {
    this.temperatureInCelcius = temperatureInCelcius;
  }

  public double getTemperatureInCelcius() {
    return temperatureInCelcius;
  }

}
