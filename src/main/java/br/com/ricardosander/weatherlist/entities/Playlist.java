package br.com.ricardosander.weatherlist.entities;

import java.util.Collections;
import java.util.List;

public class Playlist {

  private final List<Track> tracks;

  public Playlist(List<Track> tracks) {
    this.tracks = tracks;
  }

  public List<Track> getTracks() {
    return Collections.unmodifiableList(tracks);
  }

}
