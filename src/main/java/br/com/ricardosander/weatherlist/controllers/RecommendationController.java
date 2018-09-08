package br.com.ricardosander.weatherlist.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/recommendation")
public class RecommendationController {

  @RequestMapping(method = RequestMethod.GET, value = "/city/{cityName}")
  public ResponseEntity<String> getPlaylistByCityName(@PathVariable String cityName) {
    return ResponseEntity.ok().body("City name : " + cityName);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/coordinates")
  public ResponseEntity<String> getPlaylistByGeographicCoordinates(@RequestParam double latitude,
      @RequestParam double longitude) {
    return ResponseEntity.ok().body("latitude : " +latitude + ", longitude: " + longitude);
  }

}
