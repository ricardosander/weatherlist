package br.com.ricardosander.weatherlist.controllers;

import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/recommendation")
public class RecommendationController {

  private final RecommendationService recommendationService;

  @Autowired
  public RecommendationController(
      RecommendationService recommendationService) {
    this.recommendationService = recommendationService;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/city/{cityName}")
  public ResponseEntity<Playlist> getPlaylistByCityName(@PathVariable String cityName) {
    return ResponseEntity.ok().body(recommendationService.getPlaylist(cityName));
  }

  @RequestMapping(method = RequestMethod.GET, value = "/coordinates")
  public ResponseEntity<Playlist> getPlaylistByGeographicCoordinates(@RequestParam double latitude,
      @RequestParam double longitude) {

    GeographicCoordinate geCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    return ResponseEntity.ok().body(recommendationService.getPlaylist(geCoordinate));
  }

}
