package br.com.ricardosander.weatherlist.controllers;

import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.services.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/playlist")
public class PlaylistController {

  private final Logger logger = LoggerFactory.getLogger(PlaylistController.class);

  private final RecommendationService recommendationService;

  @Autowired
  public PlaylistController(
      RecommendationService recommendationService) {
    logger.info("Creating RecommendationController");
    this.recommendationService = recommendationService;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/city/{cityName}")
  public ResponseEntity<Playlist> getPlaylistByCityName(@PathVariable String cityName) {
    logger.info("Requesting playlist by city name " + cityName);
    return ResponseEntity.ok().body(recommendationService.getPlaylistByCityName(cityName));
  }

  @RequestMapping(method = RequestMethod.GET, value = "/coordinates")
  public ResponseEntity<Playlist> getPlaylistByGeographicCoordinates(@RequestParam double latitude,
      @RequestParam double longitude) {

    logger.info("Requesting playlist by geographic coordinates " + latitude + ", " + longitude);

    GeographicCoordinate geCoordinate = GeographicCoordinate.newInstance(latitude, longitude);

    return ResponseEntity.ok().body(recommendationService.getPlaylist(geCoordinate));
  }

}
