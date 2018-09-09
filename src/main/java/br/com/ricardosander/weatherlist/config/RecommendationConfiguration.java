package br.com.ricardosander.weatherlist.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.entities.Track;
import br.com.ricardosander.weatherlist.entities.Weather;
import br.com.ricardosander.weatherlist.services.RecommendationService;
import br.com.ricardosander.weatherlist.services.RecommendationServiceImplementation;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

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

    WeatherAPI weatherAPI = Mockito.mock(WeatherAPI.class);

    Weather weather = new Weather(20.0);

    when(weatherAPI.findWeather(any(GeographicCoordinate.class))).thenReturn(weather);
    when(weatherAPI.findWeatherByCityName(any(String.class))).thenReturn(weather);

    return weatherAPI;
  }

  @Bean
  public PlaylistAPI playlistAPI() {

    PlaylistAPI playlistAPI = Mockito.mock(PlaylistAPI.class);

    Playlist playlist = new Playlist(
        Arrays.asList(new Track("Tourniquet"), new Track("Going Under"),
            new Track("Bring me to Life")));

    when(playlistAPI.find(any(Category.class))).thenReturn(playlist);

    return playlistAPI;
  }

}
