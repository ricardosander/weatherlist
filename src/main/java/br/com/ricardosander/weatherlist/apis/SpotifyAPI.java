package br.com.ricardosander.weatherlist.apis;

import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.entities.Track;
import br.com.ricardosander.weatherlist.services.exceptions.ObjectNotFoundException;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpotifyAPI implements PlaylistAPI {

  private final SpotifyApi spotifyApi;

  public SpotifyAPI(String spotifyClientId, String spotifySecret) {

    spotifyApi = SpotifyApi.builder()
        .setClientId(spotifyClientId)
        .setClientSecret(spotifySecret)
        .build();
  }

  @Override
  public Playlist find(Category category) {

    try {

      if (!isAuthenticated()) {
        authenticate();
      }

      return new Playlist(getTracks(spotifyApi, category.getLowerCase()));

    } catch (SpotifyWebApiException | IOException e) {
      e.printStackTrace();//TODO handle
    }

    throw new ObjectNotFoundException("Playlist for " + category + " not found.");
  }

  private void authenticate() {

    ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
    ClientCredentials clientCredentials = null;

    try {
      clientCredentials = clientCredentialsRequest.execute();
    } catch (IOException | SpotifyWebApiException e) {
      e.printStackTrace();//TODO handle
    }

    spotifyApi.setAccessToken(clientCredentials.getAccessToken());
  }

  private boolean isAuthenticated() {
    return spotifyApi.getAccessToken() != null && !spotifyApi.getAccessToken().isEmpty();
  }

  private List<Track> getTracks(SpotifyApi spotifyApi, String category)
      throws IOException, SpotifyWebApiException {

    Paging<PlaylistSimplified> party =
        spotifyApi.getCategorysPlaylists(category).build().execute();

    String playListId = party.getItems()[0].getId();
    String userId = party.getItems()[0].getOwner().getId();

    com.wrapper.spotify.model_objects.specification.Playlist playlist =
        spotifyApi.getPlaylist(userId, playListId).build().execute();

    PlaylistTrack[] tracks = playlist.getTracks().getItems();

    List<Track> myTracks = new ArrayList<>();
    for (PlaylistTrack track : tracks) {
      myTracks.add(new Track(track.getTrack().getName()));
    }
    return myTracks;
  }

}
