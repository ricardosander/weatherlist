package br.com.ricardosander.weatherlist.config;

import static org.mockito.Mockito.when;

import br.com.ricardosander.weatherlist.apis.OpenWeatherMapAPI;
import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.entities.Track;
import br.com.ricardosander.weatherlist.services.RecommendationService;
import br.com.ricardosander.weatherlist.services.RecommendationServiceImplementation;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    PlaylistAPI playlistAPI = Mockito.mock(PlaylistAPI.class);

    when(playlistAPI.find(Category.PARTY)).thenReturn(createPlaylist(PARTY_SONG_NAMES));
    when(playlistAPI.find(Category.POP)).thenReturn(createPlaylist(POP_SONG_NAMES));
    when(playlistAPI.find(Category.ROCK)).thenReturn(createPlaylist(ROCK_SONG_NAMES));
    when(playlistAPI.find(Category.CLASSIC)).thenReturn(createPlaylist(CLASSIC_SONG_NAMES));

    return playlistAPI;
  }

  private Playlist createPlaylist(List<String> songNames) {
    return new Playlist(songNames.stream().map(Track::new).collect(Collectors.toList()));
  }

  private static final List<String> PARTY_SONG_NAMES =
      Arrays.asList("Party 1", "Party 2", "Don't Stop the Party");

  private static final List<String> POP_SONG_NAMES = Arrays.asList("Pop 1", "Pop 2", "Pop Pop Pop");

  private static final List<String> ROCK_SONG_NAMES =
      Arrays.asList("Rock 1", "Rock n Roll", "I Gonna Rock Your World");

  private static final List<String> CLASSIC_SONG_NAMES =
      Arrays.asList("Classic 1", "The Classic of the Classics", "Spring Framework");

}
