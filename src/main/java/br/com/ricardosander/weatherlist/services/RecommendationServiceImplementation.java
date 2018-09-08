package br.com.ricardosander.weatherlist.services;

import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.entities.Track;

import java.util.Collections;

public class RecommendationServiceImplementation implements RecommendationService {

  @Override
  public Playlist getPlaylist(GeographicCoordinate geographicCoordinate) {
    return new Playlist(Collections.singletonList(new Track(
        "Song from latitude : " + geographicCoordinate.getLatitude() + ", longitude: "
            + geographicCoordinate.getLongitude())));
  }

  @Override
  public Playlist getPlaylist(String cityName) {
    return new Playlist(Collections.singletonList(new Track("Song from " + cityName)));
  }

}