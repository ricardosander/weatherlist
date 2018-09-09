package br.com.ricardosander.weatherlist.config;

import br.com.ricardosander.weatherlist.apis.OpenWeatherMapAPI;
import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.SpotifyAPI;
import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.services.RecommendationService;
import br.com.ricardosander.weatherlist.services.RecommendationServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecommendationConfiguration {

  @Bean
  @Autowired
  public RecommendationService recommendationService(WeatherAPI weatherAPI,
      PlaylistAPI playlistAPI) {
    return new RecommendationServiceImplementation(weatherAPI, playlistAPI);
  }

  @Bean
  public WeatherAPI weatherAPI() {
    return new OpenWeatherMapAPI();
  }

  @Bean
  public PlaylistAPI playlistAPI() {
    return new SpotifyAPI();
  }

}
