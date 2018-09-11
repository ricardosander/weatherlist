package br.com.ricardosander.weatherlist.apis.openweatherapi;

import br.com.ricardosander.weatherlist.apis.WeatherApiConfiguration;

public class OpenWeatherMapConfiguration implements WeatherApiConfiguration {

  private final String key;

  public OpenWeatherMapConfiguration(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
