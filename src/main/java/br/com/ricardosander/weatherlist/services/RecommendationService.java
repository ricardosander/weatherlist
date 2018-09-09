package br.com.ricardosander.weatherlist.services;

import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Playlist;

public interface RecommendationService {

  Playlist getPlaylist(GeographicCoordinate geographicCoordinate);

  Playlist getPlaylistByCityName(String cityName);

}