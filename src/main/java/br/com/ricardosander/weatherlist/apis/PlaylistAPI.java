package br.com.ricardosander.weatherlist.apis;

import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;
import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.Playlist;

/**
 * API responsible for retrieving a playlist based on a category.
 */
public interface PlaylistAPI {

  /**
   * Retrieves a playlist given a category.
   * @param category category for searching on API.
   * @return
   * @throws ApiUnavailableException
   */
  Playlist find(Category category) throws ApiUnavailableException;

}
