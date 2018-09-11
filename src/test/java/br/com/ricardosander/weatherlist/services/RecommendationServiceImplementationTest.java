package br.com.ricardosander.weatherlist.services;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;
import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
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

  private static final double VERY_HOT_LATITUDE = 11.26;
  private static final double VERY_HOT_LONGITUDE = 13.47;
  private static final double HOT_LATITUDE = -15.68;
  private static final double HOT_LONGITUDE = 17.89;
  private static final double CHILLY_LATITUDE = 20.90;
  private static final double CHILLY_LONGITUDE = -21.11;
  private static final double FREEZING_LATITUDE = -21.52;
  private static final double FREEZING_LONGITUDE = -21.43;
  private static final double NOT_FOUND_LATITUDE = 21.45;
  private static final double NOT_FOUND_LONGITUDE = 21.52;

  private final GeographicCoordinate veryHotGeographicCoordinate =
      GeographicCoordinate.newInstance(VERY_HOT_LATITUDE, VERY_HOT_LONGITUDE);

  private final GeographicCoordinate hotGeographicCoordinate =
      GeographicCoordinate.newInstance(HOT_LATITUDE, HOT_LONGITUDE);

  private final GeographicCoordinate chillyGeographicCoordinate =
      GeographicCoordinate.newInstance(CHILLY_LATITUDE, CHILLY_LONGITUDE);

  private final GeographicCoordinate freezingGeographicCoordinate =
      GeographicCoordinate.newInstance(FREEZING_LATITUDE, FREEZING_LONGITUDE);

  private final GeographicCoordinate notFoundGeographicCoordinate =
      GeographicCoordinate.newInstance(NOT_FOUND_LATITUDE, NOT_FOUND_LONGITUDE);

  private RecommendationService recommendationService;

  @Before
  public void setUp() throws ApiUnavailableException {

    WeatherAPI weatherAPI = mock(WeatherAPI.class);

    double veryHotTemperatureInCelcius = 31.0;
    Weather veryHotWeather = new Weather(veryHotTemperatureInCelcius);
    when(weatherAPI.findWeatherByCityName(VERY_HOT_CITY_NAME)).thenReturn(veryHotWeather);
    when(weatherAPI.findWeather(veryHotGeographicCoordinate)).thenReturn(veryHotWeather);

    double hotTemperatureInCelcius = 28.0;
    Weather hotWeather = new Weather(hotTemperatureInCelcius);
    when(weatherAPI.findWeatherByCityName(HOT_CITY_NAME)).thenReturn(hotWeather);
    when(weatherAPI.findWeather(hotGeographicCoordinate)).thenReturn(hotWeather);

    double chillyTemperatureInCelcius = 12.0;
    Weather chillyWeather = new Weather(chillyTemperatureInCelcius);
    when(weatherAPI.findWeatherByCityName(CHILLY_CITY_NAME)).thenReturn(chillyWeather);
    when(weatherAPI.findWeather(chillyGeographicCoordinate)).thenReturn(chillyWeather);

    double freezingTemperatureInCelcius = 6.0;
    Weather freezingWeather = new Weather(freezingTemperatureInCelcius);
    when(weatherAPI.findWeatherByCityName(FREEZING_CITY_NAME)).thenReturn(freezingWeather);
    when(weatherAPI.findWeather(freezingGeographicCoordinate)).thenReturn(freezingWeather);

    when(weatherAPI.findWeatherByCityName(NOT_REAL_CITY_NAME)).thenThrow(
        ObjectNotFoundException.class);
    when(weatherAPI.findWeather(notFoundGeographicCoordinate)).thenThrow(
        ObjectNotFoundException.class);

    PlaylistAPI playlistAPI = mock(PlaylistAPI.class);
    when(playlistAPI.find(Category.PARTY)).thenReturn(createPlaylist(PARTY_SONG_NAMES));
    when(playlistAPI.find(Category.POP)).thenReturn(createPlaylist(POP_SONG_NAMES));
    when(playlistAPI.find(Category.ROCK)).thenReturn(createPlaylist(ROCK_SONG_NAMES));
    when(playlistAPI.find(Category.CLASSICAL)).thenReturn(createPlaylist(CLASSIC_SONG_NAMES));

    recommendationService = new RecommendationServiceImplementation(weatherAPI, playlistAPI);
  }

  @Test
  public void shouldReturnPartyPlaylistForVeryHotCityName() {

    Playlist playlist = recommendationService.getPlaylistByCityName(VERY_HOT_CITY_NAME);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), PARTY_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(PARTY_SONG_NAMES));
  }

  @Test
  public void shouldReturnPartyPlaylistForVeryHotGeoCoordinates() {

    Playlist playlist = recommendationService.getPlaylist(veryHotGeographicCoordinate);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), PARTY_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(PARTY_SONG_NAMES));
  }

  @Test
  public void shouldReturnPopPlaylistForHotCityName() {

    Playlist playlist = recommendationService.getPlaylistByCityName(HOT_CITY_NAME);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), POP_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(POP_SONG_NAMES));
  }

  @Test
  public void shouldReturnPopPlaylistForHotGeoCoordinates() {

    Playlist playlist = recommendationService.getPlaylist(hotGeographicCoordinate);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), POP_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(POP_SONG_NAMES));
  }

  @Test
  public void shouldReturnRockPlaylistForChillyCityName() {

    Playlist playlist = recommendationService.getPlaylistByCityName(CHILLY_CITY_NAME);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), ROCK_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(ROCK_SONG_NAMES));
  }

  @Test
  public void shouldReturnRockPlaylistForChillyGeoCoordinates() {

    Playlist playlist = recommendationService.getPlaylist(chillyGeographicCoordinate);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), ROCK_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(ROCK_SONG_NAMES));
  }

  @Test
  public void shouldReturnClassicPlaylistForFreezingCityName() {

    Playlist playlist = recommendationService.getPlaylistByCityName(FREEZING_CITY_NAME);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), CLASSIC_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(CLASSIC_SONG_NAMES));
  }

  @Test
  public void shouldReturnClassicPlaylistForFreezingGeoCoordinates() {

    Playlist playlist = recommendationService.getPlaylist(freezingGeographicCoordinate);

    assertNotNull(playlist);
    assertNotNull(playlist.getTracks());
    assertEquals(playlist.getTracks().size(), CLASSIC_SONG_NAMES.size());
    assertThat(playlist.getTracks().stream().map(Track::getName).collect(Collectors.toList()),
        is(CLASSIC_SONG_NAMES));
  }

  @Test(expected = ObjectNotFoundException.class)
  public void shouldThrowExceptionForInvalidCityName() {

    Playlist playlist = recommendationService.getPlaylistByCityName(NOT_REAL_CITY_NAME);

    assertNotNull(playlist);
  }

  @Test(expected = ObjectNotFoundException.class)
  public void shouldThrowExceptionForInvalidGeoCoordinates() {

    Playlist playlist = recommendationService.getPlaylist(notFoundGeographicCoordinate);

    assertNotNull(playlist);
  }

  private Playlist createPlaylist(List<String> songNames) {
    return new Playlist(songNames.stream().map(Track::new).collect(Collectors.toList()));
  }

}