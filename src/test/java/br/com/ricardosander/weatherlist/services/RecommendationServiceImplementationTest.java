package br.com.ricardosander.weatherlist.services;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.entities.Track;
import br.com.ricardosander.weatherlist.entities.Weather;
import br.com.ricardosander.weatherlist.services.exceptions.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationServiceImplementationTest {

  private static final String VERY_HOT_CITY_NAME = "Natal";
  private static final String HOT_CITY_NAME = "Campinas";
  private static final String CHILLY_CITY_NAME = "Curitiba";
  private static final String FREEZING_CITY_NAME = "Porto Alegre";
  private static final String NOT_REAL_CITY_NAME = "Not a Real City";

  private static final List<String> PARTY_SONG_NAMES =
      Arrays.asList("Party 1", "Party 2", "Don't Stop the Party");

  private static final List<String> POP_SONG_NAMES = Arrays.asList("Pop 1", "Pop 2", "Pop Pop Pop");

  private static final List<String> ROCK_SONG_NAMES =
      Arrays.asList("Rock 1", "Rock n Roll", "I Gonna Rock Your World");

  private static final List<String> CLASSIC_SONG_NAMES =
      Arrays.asList("Classic 1", "The Classic of the Classics", "Spring Framework");

  private WeatherAPI weatherAPI;

  private PlaylistAPI playlistAPI;

  private RecommendationService recommendationService;

  @Before
  public void setUp() {

    weatherAPI = mock(WeatherAPI.class);

    double veryHotTemperatureInCelcius = 31.0;
    Weather veryHotWeather = new Weather(veryHotTemperatureInCelcius);
    when(weatherAPI.findWeatherByCityName(VERY_HOT_CITY_NAME)).thenReturn(veryHotWeather);

    double hotTemperatureInCelcius = 28.0;
    Weather hotWeather = new Weather(hotTemperatureInCelcius);
    when(weatherAPI.findWeatherByCityName(HOT_CITY_NAME)).thenReturn(hotWeather);

    double chillyTemperatureInCelcius = 12.0;
    Weather chillyWeather = new Weather(chillyTemperatureInCelcius);
    when(weatherAPI.findWeatherByCityName(CHILLY_CITY_NAME)).thenReturn(chillyWeather);

    double freezingTemperatureInCelcius = 6.0;
    Weather freezingWeather = new Weather(freezingTemperatureInCelcius);
    when(weatherAPI.findWeatherByCityName(FREEZING_CITY_NAME)).thenReturn(freezingWeather);

    when(weatherAPI.findWeatherByCityName(NOT_REAL_CITY_NAME)).thenThrow(
        ObjectNotFoundException.class);

    playlistAPI = mock(PlaylistAPI.class);
    when(playlistAPI.find(Category.PARTY)).thenReturn(createPlaylist(PARTY_SONG_NAMES));
    when(playlistAPI.find(Category.POP)).thenReturn(createPlaylist(POP_SONG_NAMES));
    when(playlistAPI.find(Category.ROCK)).thenReturn(createPlaylist(ROCK_SONG_NAMES));
    when(playlistAPI.find(Category.CLASSIC)).thenReturn(createPlaylist(CLASSIC_SONG_NAMES));

    recommendationService = new RecommendationServiceImplementation(weatherAPI, playlistAPI);
  }

  @Test
  public void shouldReturnPartyPlaylistForVeryHotCityName() {

    Playlist playlist = recommendationService.getPlaylist(VERY_HOT_CITY_NAME);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), PARTY_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(PARTY_SONG_NAMES));
  }

  @Test
  public void shouldReturnPopPlaylistForHotCityName() {

    Playlist playlist = recommendationService.getPlaylist(HOT_CITY_NAME);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), POP_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(POP_SONG_NAMES));
  }

  @Test
  public void shouldReturnRockPlaylistForChillyCityName() {

    Playlist playlist = recommendationService.getPlaylist(CHILLY_CITY_NAME);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), ROCK_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(ROCK_SONG_NAMES));
  }

  @Test
  public void shouldReturnClassicPlaylistForFreezingCityName() {

    Playlist playlist = recommendationService.getPlaylist(FREEZING_CITY_NAME);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), CLASSIC_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(CLASSIC_SONG_NAMES));
  }

  @Test(expected = ObjectNotFoundException.class)
  public void shouldThrowExceptionForInvalidCityName() {

    Playlist playlist = recommendationService.getPlaylist(NOT_REAL_CITY_NAME);

    assertNotNull(playlist);
  }

  private Playlist createPlaylist(List<String> songNames) {
    return new Playlist(songNames.stream().map(Track::new).collect(Collectors.toList()));
  }

}