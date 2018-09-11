package br.com.ricardosander.weatherlist.services;

import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;
import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.entities.Track;
import br.com.ricardosander.weatherlist.entities.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RecommendationServiceImplementation implements RecommendationService {

  private final WeatherAPI weatherAPI;

  private final PlaylistAPI playlistAPI;

  private final Playlist defaultPlaylist;

  @Autowired
  public RecommendationServiceImplementation(WeatherAPI weatherAPI, PlaylistAPI playlistAPI) {
    this.weatherAPI = weatherAPI;
    this.playlistAPI = playlistAPI;
    defaultPlaylist = new Playlist(Arrays.asList(new Track("A Track"), new Track("Another Track")));
  }

  @Override
  public Playlist getPlaylist(GeographicCoordinate geographicCoordinate) {

    Weather weather = weatherAPI.findWeather(geographicCoordinate);
    Category category = Category.getInstance(weather);

    try {
      return playlistAPI.find(category);
    } catch (ApiUnavailableException e) {
      return defaultPlaylist;
    }
  }

  @Override
  public Playlist getPlaylistByCityName(String cityName) {

    Weather weather = weatherAPI.findWeatherByCityName(cityName);
    Category category = Category.getInstance(weather);

    try {
      return playlistAPI.find(category);
    } catch (ApiUnavailableException e) {
      return defaultPlaylist;
    }
  }

}