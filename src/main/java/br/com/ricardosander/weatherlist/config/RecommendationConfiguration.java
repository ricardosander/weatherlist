package br.com.ricardosander.weatherlist.config;

import br.com.ricardosander.weatherlist.apis.OpenWeatherMapAPI;
import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.spotify.SpotifyAPI;
import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.services.RecommendationService;
import br.com.ricardosander.weatherlist.services.RecommendationServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class RecommendationConfiguration {

  private final String spotifyClientId;

  private final String spotifySecret;

  public RecommendationConfiguration(@Value("${spotify.clientId}") String spotifyClientId,
      @Value("${spotify.secret}") String spotifySecret) {
    this.spotifyClientId = spotifyClientId;
    this.spotifySecret = spotifySecret;
  }

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
    return new SpotifyAPI(spotifyClientId, spotifySecret);
  }

}
