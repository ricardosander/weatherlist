package br.com.ricardosander.weatherlist.entities;

import java.util.Collections;
import java.util.List;

/**
 * Playlist to be returned by the application.
 */
public class Playlist {

  /**
   * Playlist's tracks
   */
  private final List<Track> tracks;

  /**
   * @param tracks Playlist's tracks
   */
  public Playlist(List<Track> tracks) {
    this.tracks = tracks;
  }

  public List<Track> getTracks() {
    return Collections.unmodifiableList(tracks);
  }

}
