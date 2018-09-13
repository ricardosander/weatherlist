package br.com.ricardosander.weatherlist.services;

import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Playlist;

/**
 * Represents a service responsible by retrieving playlist based on a location.
 */
public interface RecommendationService {

  /**
   * Retrieves a playlist based on a geographic coordinates.
   * @param geographicCoordinate geographic coordinates for playlist search.
   * @return
   */
  Playlist getPlaylist(GeographicCoordinate geographicCoordinate);

  /**
   * Retrieves a playlist based on a city name.
   * @param cityName city name for playlist search.
   * @return
   */
  Playlist getPlaylistByCityName(String cityName);

}