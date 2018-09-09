package br.com.ricardosander.weatherlist.services;

import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.entities.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationServiceImplementation implements RecommendationService {

  private final WeatherAPI weatherAPI;

  private final PlaylistAPI playlistAPI;

  @Autowired
  public RecommendationServiceImplementation(WeatherAPI weatherAPI, PlaylistAPI playlistAPI) {
    this.weatherAPI = weatherAPI;
    this.playlistAPI = playlistAPI;
  }

  @Override
  public Playlist getPlaylist(GeographicCoordinate geographicCoordinate) {

    Weather weather = weatherAPI.findWeather(geographicCoordinate);
    Category category = Category.getInstance(weather);

    return playlistAPI.find(category);
  }

  @Override
  public Playlist getPlaylistByCityName(String cityName) {

    Weather weather = weatherAPI.findWeatherByCityName(cityName);
    Category category = Category.getInstance(weather);

    return playlistAPI.find(category);
  }

}