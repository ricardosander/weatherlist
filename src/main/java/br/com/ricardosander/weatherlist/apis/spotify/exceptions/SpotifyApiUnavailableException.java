package br.com.ricardosander.weatherlist.apis.spotify.exceptions;

import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;

public class SpotifyApiUnavailableException extends ApiUnavailableException {

  public SpotifyApiUnavailableException(String message) {
    super(message);
  }

}
