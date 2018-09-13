package br.com.ricardosander.weatherlist.config;

import br.com.ricardosander.weatherlist.apis.PlaylistApiConfiguration;
import br.com.ricardosander.weatherlist.apis.openweatherapi.OpenWeatherMapAPI;
import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.apis.WeatherApiConfiguration;
import br.com.ricardosander.weatherlist.apis.openweatherapi.OpenWeatherMapConfiguration;
import br.com.ricardosander.weatherlist.apis.spotify.SpotifyAPI;
import br.com.ricardosander.weatherlist.apis.spotify.SpotifyConfiguration;
import br.com.ricardosander.weatherlist.entities.DefaultPlaylist;
import br.com.ricardosander.weatherlist.entities.Track;
import br.com.ricardosander.weatherlist.services.RecommendationService;
import br.com.ricardosander.weatherlist.services.RecommendationServiceImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:application.properties")
public class RecommendationConfiguration {

  private final Logger logger = LoggerFactory.getLogger(RecommendationConfiguration.class);

  @Bean
  @Autowired
  public RecommendationService recommendationService(WeatherAPI weatherAPI,
      PlaylistAPI playlistAPI, DefaultPlaylist defaultPlaylist) {
    logger.info("Creating RecommendationService");
    return new RecommendationServiceImplementation(weatherAPI, playlistAPI, defaultPlaylist);
  }

  @Bean
  @Autowired
  public WeatherAPI weatherAPI(WeatherApiConfiguration weatherApiConfiguration) {
    logger.info("Creating WeatherAPI");
    return new OpenWeatherMapAPI((OpenWeatherMapConfiguration) weatherApiConfiguration);
  }

  @Bean
  @Autowired
  public PlaylistAPI playlistAPI(PlaylistApiConfiguration configuration) {
    logger.info("Creating PlaylistAPI");
    return new SpotifyAPI((SpotifyConfiguration) configuration);
  }

  @Bean
  public SpotifyConfiguration spotifyConfiguration(
      @Value("${spotify.clientId}") String clientId,
      @Value("${spotify.secret}") String secret,
      @Value("${spotify.limitPlaylistTracks}") int limitPlaylistTracks,
      @Value("${spotify.playListCacheInfoTimeInMinutes}") int playListCacheInfoTimeInMinutes,
      @Value("${spotify.playListCacheTimeInMinutes}") int playListCacheTimeInMinutes) {
    logger.info("Creating SpotifyConfiguration");
    return new SpotifyConfiguration(clientId, secret, limitPlaylistTracks,
        playListCacheInfoTimeInMinutes, playListCacheTimeInMinutes);
  }

  @Bean
  public OpenWeatherMapConfiguration openWeatherMapConfiguration(
      @Value("${openWeatherMap.key}") String key,
      @Value("${openWeatherMap.cityCacheTimeInMinutes}")
          int cityCacheTimeInMinutes,
      @Value("${openWeatherMap.geoCoordinatesCacheTimeInMinutes}")
          int geoCoordinatesCacheTimeInMinutes,
      @Value("${openWeatherMap.cityCacheSize}") int cityCacheSize,
      @Value("${openWeatherMap.geoCoordinatesCacheTimeInMinutes}")
          int geoCoordinatesCacheSize) {
    logger.info("Creating OpenWeatherMapConfiguration");
    return new OpenWeatherMapConfiguration(key, cityCacheTimeInMinutes,
        geoCoordinatesCacheTimeInMinutes, cityCacheSize, geoCoordinatesCacheSize);
  }

  @Bean
  public DefaultPlaylist defaultPlaylist(@Value("${defaultTracks}") String defaultTracks) {

    logger.info("Creating default playlist.");

    return new DefaultPlaylist(
        Arrays.asList(defaultTracks.split(",")).stream().map(Track::new).collect(
            Collectors.toList()));
  }

}
