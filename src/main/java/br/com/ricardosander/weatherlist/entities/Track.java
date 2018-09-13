package br.com.ricardosander.weatherlist.entities;

/**
 * Represents a track from a playlist.
 */
public class Track {

  /**
   * Track's name.
   */
  private final String name;

  /**
   * @param name Track's name.
   */
  public Track(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

}
