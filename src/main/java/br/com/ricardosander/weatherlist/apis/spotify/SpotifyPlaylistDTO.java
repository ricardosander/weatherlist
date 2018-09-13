package br.com.ricardosander.weatherlist.apis.spotify;

/**
 * DTO for Playlist info from Spotify API.
 */
class SpotifyPlaylistDTO {

  /**
   * Playlist's id on Spotify API.
   */
  private final String id;

  /**
   * Playlist's owner id on Spotify API.
   */
  private final String ownerId;

  /**
   * @param id Playlist's id on Spotify API.
   * @param ownerId Playlist's owner id on Spotify API.
   */
  SpotifyPlaylistDTO(String id, String ownerId) {
    this.id = id;
    this.ownerId = ownerId;
  }

  String getId() {
    return id;
  }

  String getOwnerId() {
    return ownerId;
  }

}