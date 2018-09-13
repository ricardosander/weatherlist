package br.com.ricardosander.weatherlist.apis.spotify;

import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;

/**
 * Identifies an Spotify API error.
 */
class SpotifyApiUnavailableException extends ApiUnavailableException {

  /**
   * @param message excepetion message.
   */
  SpotifyApiUnavailableException(String message) {
    super(message);
  }

}
