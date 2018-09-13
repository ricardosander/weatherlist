package br.com.ricardosander.weatherlist.apis.spotify;

import br.com.ricardosander.weatherlist.apis.PlaylistApiConfiguration;

/**
 * Spotify API configuration.
 */
public class SpotifyConfiguration implements PlaylistApiConfiguration {

  /**
   * Spotify cliente id
   */
  private final String clientId;

  /**
   * Spotify secret
   */
  private final String secret;

  /**
   * Limit of tracks by playlist.
   */
  private final int limitPlaylistTracks;

  /**
   * Cache of playlist information in minutes.
   */
  private final int playListCacheInfoTimeInMinutes;

  /**
   * Cache of playlist in minutes.
   */
  private final int playListCacheTimeInMinutes;

  /**
   * @param clientId Spotify cliente id
   * @param secret Spotify secret
   * @param limitPlaylistTracks Limit of tracks by playlist.
   * @param playListCacheInfoTimeInMinutes Cache of playlist information in minutes.
   * @param playListCacheTimeInMinutes Cache of playlist in minutes.
   */
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
