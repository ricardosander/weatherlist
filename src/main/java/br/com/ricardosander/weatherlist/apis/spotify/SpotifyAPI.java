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

/**
 * It is a wrapper for thelinmichael/spotify-web-api-java library.
 * Responsible for calling the Spotify API and caching the returning values.
 */
public class SpotifyAPI implements PlaylistAPI {

  private final Logger logger = LoggerFactory.getLogger(SpotifyAPI.class);

  /**
   * Limit of playlist to search for each category.
   */
  private static final int LIMIT_PLAYLIST_TO_SEARCH = 1;

  /**
   * Data searched from playlists.
   */
  private static final String TRACK_FIELDS_TO_RETRIEVE = "items(track.name)";

  /**
   * thelinmichael/spotify-web-api-java library main point.
   */
  private final SpotifyApi spotifyApi;

  /**
   * Playlist info cache by category.
   */
  private final Cache<Category, SpotifyPlaylistDTO> playlistInfoCache;


  /**
   * Playlist cache by category.
   */
  private final Cache<Category, Playlist> playlistCache;

  /**
   * Limit of tracks to each playlist.
   */
  private final int limitPlaylistTracks;

  /**
   * @param configuration Configuration needed for caching and connecting to API.
   */
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

  /**
   * Tries to authenticate to Spotify API.
   * @throws SpotifyApiUnavailableException
   */
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

  /**
   * Verifies if it is authenticate into the API.
   * @return
   */
  private boolean isAuthenticated() {
    return spotifyApi.getAccessToken() != null && !spotifyApi.getAccessToken().isEmpty();
  }

  /**
   * Get a Playlist from the API based on the category
   * @param category category for API searching of playlist.
   * @return
   * @throws IOException
   * @throws SpotifyWebApiException
   * @throws ExecutionException
   */
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

  /**
   * Retrieves Playlist info from the API.
   * @param category category for API search.
   * @return
   * @throws ExecutionException
   */
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

  /**
   * Retrieves the tracks information for a playlost from the API.
   * @param spotifyPlaylist playlist information from the API.
   * @return
   * @throws IOException
   * @throws SpotifyWebApiException
   */
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

  /**
   * Convert tracks from API into a list of applications tracks.
   * @param spotifyTracks API returned tracks.
   * @return
   */
  private List<Track> convertTracks(PlaylistTrack[] spotifyTracks) {

    List<Track> applicationTracks = new ArrayList<>();
    for (PlaylistTrack spotifyTrack : spotifyTracks) {
      applicationTracks.add(convertTrack(spotifyTrack));
    }

    return applicationTracks;
  }

  /**
   * Convert a track from API into an applications track.
   * @param spotifyTrack API returned track.
   * @return
   */
  private Track convertTrack(PlaylistTrack spotifyTrack) {
    return new Track(spotifyTrack.getTrack().getName());
  }

}
