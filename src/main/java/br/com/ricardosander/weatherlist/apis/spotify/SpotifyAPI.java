package br.com.ricardosander.weatherlist.apis.spotify;

import br.com.ricardosander.weatherlist.apis.PlaylistAPI;
import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;
import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.entities.Track;
import br.com.ricardosander.weatherlist.services.exceptions.ObjectNotFoundException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SpotifyAPI implements PlaylistAPI {

  private final Logger logger = LoggerFactory.getLogger(SpotifyAPI.class);

  private static final int LIMIT_PLAYLIST_TO_SEARCH = 1;

  private static final String TRACK_FIELDS_TO_RETRIEVE = "items(track.name)";

  private final SpotifyApi spotifyApi;

  private final Cache<Category, SpotifyPlaylistDTO> playlistInfoCache;

  private final Cache<Category, Playlist> playlistCache;

  private final int limitPlaylistTracks;

  public SpotifyAPI(SpotifyConfiguration configuration) {

    spotifyApi = SpotifyApi.builder()
        .setClientId(configuration.getClientId())
        .setClientSecret(configuration.getSecret())
        .build();

    playlistInfoCache = CacheBuilder
        .newBuilder()
        .expireAfterWrite(configuration.getPlayListCacheInfoTimeInMinutes(), TimeUnit.MINUTES)
        .build();

    playlistCache = CacheBuilder
        .newBuilder()
        .expireAfterWrite(configuration.getPlayListCacheTimeInMinutes(), TimeUnit.MINUTES)
        .build();

    limitPlaylistTracks = configuration.getLimitPlaylistTracks();
  }

  @Override
  public Playlist find(Category category) throws ApiUnavailableException {

    try {

      return playlistCache.get(category, () -> {

            logger.info("Calling Spotify API : Playlist by category " + category);
            if (!isAuthenticated()) {
              authenticate();
            }

            logger.info("Caching Playlist for category " + category);
            return getPlaylist(category);
          }
      );

    } catch (ExecutionException e) {
      logger.error(e.getMessage());
      spotifyApi.setAccessToken(null);
      throw new SpotifyApiUnavailableException(e.getMessage());
    }

  }

  private void authenticate() throws SpotifyApiUnavailableException {

    ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
    ClientCredentials clientCredentials = null;

    try {

      logger.info("Authenticating on Spotify API.");
      clientCredentials = clientCredentialsRequest.execute();

    } catch (IOException | SpotifyWebApiException e) {
      logger.error(e.getMessage());
      throw new SpotifyApiUnavailableException(e.getMessage());
    }

    logger.info("Registering Spotify access token.");
    spotifyApi.setAccessToken(clientCredentials.getAccessToken());
  }

  private boolean isAuthenticated() {
    return spotifyApi.getAccessToken() != null && !spotifyApi.getAccessToken().isEmpty();
  }

  private Playlist getPlaylist(Category category)
      throws IOException, SpotifyWebApiException, ExecutionException {

    SpotifyPlaylistDTO spotifyPlaylist = getPlaylistInfo(category);

    PlaylistTrack[] tracks = getPlaylistTracks(spotifyPlaylist);

    List<Track> myTracks = convertTracks(tracks);

    if (myTracks.isEmpty()) {
      logger.info("Playlist for category " + category + " not found.");
      throw new ObjectNotFoundException("Playlist for " + category + " not found.");
    }

    return new Playlist(myTracks);
  }

  private SpotifyPlaylistDTO getPlaylistInfo(Category category) throws ExecutionException {

    return
        playlistInfoCache.get(category, () -> {

          logger.info("Calling Spotify API by category " + category);

          Paging<PlaylistSimplified> playlistResult =
              spotifyApi.getCategorysPlaylists(category.getLowerCase())
                  .limit(LIMIT_PLAYLIST_TO_SEARCH)
                  .build()
                  .execute();

          if (playlistResult.getItems().length == 0) {
            logger.info("Playlist for category " + category + " not found.");
            throw new ObjectNotFoundException("Playlist for " + category + " not found.");
          }

          logger.info("Caching Playlist information for category " + category);
          return new SpotifyPlaylistDTO(playlistResult.getItems()[0].getId(),
              playlistResult.getItems()[0].getOwner().getId());
        });

  }

  private PlaylistTrack[] getPlaylistTracks(SpotifyPlaylistDTO spotifyPlaylist)
      throws IOException, SpotifyWebApiException {

    Paging<PlaylistTrack> playlistTrackPaging =
        spotifyApi.getPlaylistsTracks(spotifyPlaylist.getOwnerId(), spotifyPlaylist.getId())
            .fields(TRACK_FIELDS_TO_RETRIEVE)
            .limit(limitPlaylistTracks)
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
