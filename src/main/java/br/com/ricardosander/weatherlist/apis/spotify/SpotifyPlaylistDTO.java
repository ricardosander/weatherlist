package br.com.ricardosander.weatherlist.apis.spotify;

class SpotifyPlaylistDTO {

  private final String id;

  private final String ownerId;

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