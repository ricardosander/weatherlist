package br.com.ricardosander.weatherlist.config;

import br.com.ricardosander.weatherlist.apis.PlaylistApiConfiguration;
import br.com.ricardosander.weatherlist.apis.OpenWeatherMapAPI;
import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.apis.spotify.SpotifyAPI;
import br.com.ricardosander.weatherlist.apis.spotify.SpotifyConfiguration;
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
  @Autowired
  public PlaylistAPI playlistAPI(PlaylistApiConfiguration configuration) {
    return new SpotifyAPI((SpotifyConfiguration) configuration);
  }

  @Bean
  public SpotifyConfiguration spotifyConfiguration(
      @Value("${spotify.clientId}") String clientId,
      @Value("${spotify.secret}") String secret,
      @Value("${spotify.limitPlaylistTracks}") int limitPlaylistTracks,
      @Value("${spotify.playListCacheInfoTimeInMinutes}") int playListCacheInfoTimeInMinutes,
      @Value("${spotify.playListCacheTimeInMinutes}") int playListCacheTimeInMinutes) {
    return new SpotifyConfiguration(clientId, secret, limitPlaylistTracks,
        playListCacheInfoTimeInMinutes, playListCacheTimeInMinutes);
  }

}
