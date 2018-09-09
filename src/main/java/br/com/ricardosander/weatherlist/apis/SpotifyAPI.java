package br.com.ricardosander.weatherlist.apis;

import br.com.ricardosander.weatherlist.entities.Category;
import br.com.ricardosander.weatherlist.entities.Playlist;
import br.com.ricardosander.weatherlist.entities.Track;
import br.com.ricardosander.weatherlist.services.exceptions.ObjectNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpotifyAPI implements PlaylistAPI {

  private static final List<String> PARTY_SONG_NAMES =
      Arrays.asList("Party 1", "Party 2", "Don't Stop the Party");

  private static final List<String> POP_SONG_NAMES = Arrays.asList("Pop 1", "Pop 2", "Pop Pop Pop");

  private static final List<String> ROCK_SONG_NAMES =
      Arrays.asList("Rock 1", "Rock n Roll", "I Gonna Rock Your World");

  private static final List<String> CLASSIC_SONG_NAMES =
      Arrays.asList("Classic 1", "The Classic of the Classics", "Spring Framework");

  @Override
  public Playlist find(Category category) {

    switch (category) {
      case PARTY:
        return createPlaylist(PARTY_SONG_NAMES);
      case POP:
        return createPlaylist(POP_SONG_NAMES);
      case ROCK:
        return createPlaylist(ROCK_SONG_NAMES);
      case CLASSIC:
        return createPlaylist(CLASSIC_SONG_NAMES);
    }

    throw new ObjectNotFoundException("Playlist for " + category + " not found.");
  }

  private Playlist createPlaylist(List<String> songNames) {
    return new Playlist(songNames.stream().map(Track::new).collect(Collectors.toList()));
  }

}
