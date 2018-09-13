package br.com.ricardosander.weatherlist.entities;

import java.util.List;

/**
 * Default playlist is a playlist to be returned when having trouble finding a playlist.
 */
public class DefaultPlaylist extends Playlist {

  /**
   * @param tracks playlist's tracks.
   */
  public DefaultPlaylist(List<Track> tracks) {
    super(tracks);
  }

}
