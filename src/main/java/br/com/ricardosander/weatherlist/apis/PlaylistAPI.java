package br.com.ricardosander.weatherlist.apis;

import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;
import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.Playlist;

public interface PlaylistAPI {
  Playlist find(Category category) throws ApiUnavailableException;
}
