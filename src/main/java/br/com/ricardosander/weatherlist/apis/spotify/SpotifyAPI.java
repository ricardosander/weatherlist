package br.com.ricardosander.weatherlist.apis.spotify;

import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;
import br.com.ricardosander.weatherlist.apis.spotify.exceptions.SpotifyApiUnavailableException;
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

  private static final int LIMIT_PLAYLIST_TO_SEARCH = 1;
  private static final String TRACK_FIELDS_TO_RETRIEVE = "items(track.name)";
  private static final int LIMIT_TRACKS_TO_SEARCH = 30;

  private final SpotifyApi spotifyApi;

  public SpotifyAPI(String spotifyClientId, String spotifySecret) {

    spotifyApi = SpotifyApi.builder()
        .setClientId(spotifyClientId)
        .setClientSecret(spotifySecret)
        .build();
  }

  @Override
  public Playlist find(Category category) throws ApiUnavailableException {

    try {

      if (!isAuthenticated()) {
        authenticate();
      }

      return getPlaylist(category.getLowerCase());

    } catch (SpotifyWebApiException | IOException e) {
      spotifyApi.setAccessToken(null);
      throw new SpotifyApiUnavailableException(e.getMessage());
    }

  }

  private void authenticate() throws SpotifyApiUnavailableException {

    ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
    ClientCredentials clientCredentials = null;

    try {
      clientCredentials = clientCredentialsRequest.execute();
    } catch (IOException | SpotifyWebApiException e) {
      throw new SpotifyApiUnavailableException(e.getMessage());
    }

    spotifyApi.setAccessToken(clientCredentials.getAccessToken());
  }

  private boolean isAuthenticated() {
    return spotifyApi.getAccessToken() != null && !spotifyApi.getAccessToken().isEmpty();
  }

  private Playlist getPlaylist(String category)
      throws IOException, SpotifyWebApiException {

    SpotifyPlaylistDTO spotifyPlaylist = getPlaylistInfo(category);

    PlaylistTrack[] tracks = getPlaylistTracks(spotifyPlaylist);

    List<Track> myTracks = convertTracks(tracks);

    if (myTracks.isEmpty()) {
      throw new ObjectNotFoundException("Playlist for " + category + " not found.");
    }

    return new Playlist(myTracks);
  }

  private SpotifyPlaylistDTO getPlaylistInfo(String category)
      throws IOException, SpotifyWebApiException {

    Paging<PlaylistSimplified> playlistResult = spotifyApi.getCategorysPlaylists(category)
        .limit(LIMIT_PLAYLIST_TO_SEARCH)
        .build()
        .execute();

    if (playlistResult.getItems().length == 0) {
      throw new ObjectNotFoundException("Playlist for " + category + " not found.");
    }

    return new SpotifyPlaylistDTO(playlistResult.getItems()[0].getId(),
        playlistResult.getItems()[0].getOwner().getId());
  }

  private PlaylistTrack[] getPlaylistTracks(SpotifyPlaylistDTO spotifyPlaylist)
      throws IOException, SpotifyWebApiException {

    Paging<PlaylistTrack> playlistTrackPaging =
        spotifyApi.getPlaylistsTracks(spotifyPlaylist.getOwnerId(), spotifyPlaylist.getId())
            .fields(TRACK_FIELDS_TO_RETRIEVE)
            .limit(LIMIT_TRACKS_TO_SEARCH)
            .build()
            .execute();

    return playlistTrackPaging.getItems();
  }

  private List<Track> convertTracks(PlaylistTrack[] spotifyTracks) {

    List<Track> applicationTracks = new ArrayList<>();
    for (PlaylistTrack spotifyTrack : spotifyTracks) {
      applicationTracks.add(convertTrack(spotifyTrack));
    }

    return applicationTracks;
  }

  private Track convertTrack(PlaylistTrack spotifyTrack) {
    return new Track(spotifyTrack.getTrack().getName());
  }

}
