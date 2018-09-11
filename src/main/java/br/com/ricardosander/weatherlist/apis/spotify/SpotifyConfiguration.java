package br.com.ricardosander.weatherlist.apis.spotify;

import br.com.ricardosander.weatherlist.apis.PlaylistApiConfiguration;

public class SpotifyConfiguration implements PlaylistApiConfiguration {

  private final String clientId;

  private final String secret;

  private final int limitPlaylistTracks;

  private final int playListCacheInfoTimeInMinutes;

  private final int playListCacheTimeInMinutes;

  public SpotifyConfiguration(String clientId, String secret, int limitPlaylistTracks,
      int playListCacheInfoTimeInMinutes, int playListCacheTimeInMinutes) {
    this.clientId = clientId;
    this.secret = secret;
    this.limitPlaylistTracks = limitPlaylistTracks;
    this.playListCacheInfoTimeInMinutes = playListCacheInfoTimeInMinutes;
    this.playListCacheTimeInMinutes = playListCacheTimeInMinutes;
  }

  String getClientId() {
    return clientId;
  }

  String getSecret() {
    return secret;
  }

  int getLimitPlaylistTracks() {
    return limitPlaylistTracks;
  }

  int getPlayListCacheInfoTimeInMinutes() {
    return playListCacheInfoTimeInMinutes;
  }

  int getPlayListCacheTimeInMinutes() {
    return playListCacheTimeInMinutes;
  }

}
