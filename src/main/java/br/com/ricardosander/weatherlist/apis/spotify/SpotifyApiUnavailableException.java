package br.com.ricardosander.weatherlist.apis.spotify;

import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;

class SpotifyApiUnavailableException extends ApiUnavailableException {

  SpotifyApiUnavailableException(String message) {
    super(message);
  }

}
