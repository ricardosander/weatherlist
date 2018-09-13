package br.com.ricardosander.weatherlist.services;

import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;
import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.DefaultPlaylist;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.entities.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationServiceImplementation implements RecommendationService {

  private final Logger logger = LoggerFactory.getLogger(RecommendationServiceImplementation.class);

  /**
   * API for weather search.
   */
  private final WeatherAPI weatherAPI;

  /**
   * API for playlist search.
   */
  private final PlaylistAPI playlistAPI;

  /**
   * Default playlist to be returned on failures or playlist not being found.
   */
  private final DefaultPlaylist defaultPlaylist;

  @Autowired
  public RecommendationServiceImplementation(WeatherAPI weatherAPI, PlaylistAPI playlistAPI,
      DefaultPlaylist defaultPlaylist) {
    logger.info("Creating RecommendationServiceImplementation");
    this.weatherAPI = weatherAPI;
    this.playlistAPI = playlistAPI;
    this.defaultPlaylist = defaultPlaylist;
  }

  @Override
  public Playlist getPlaylist(GeographicCoordinate geographicCoordinate) {

    try {

      Weather weather = weatherAPI.findWeather(geographicCoordinate);
      Category category = Category.getInstance(weather);

      return playlistAPI.find(category);
    } catch (ApiUnavailableException e) {
      logger.info("Returning default playlist for " + geographicCoordinate.toString());
      return defaultPlaylist;
    }
  }

  @Override
  public Playlist getPlaylistByCityName(String cityName) {

    try {

      Weather weather = weatherAPI.findWeatherByCityName(cityName);
      Category category = Category.getInstance(weather);

      return playlistAPI.find(category);
    } catch (ApiUnavailableException e) {
      logger.info("Returning default playlist for city name " + cityName);
      return defaultPlaylist;
    }
  }

}