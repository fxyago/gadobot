package br.com.yagofx.gadobot.service;

import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Optional;

public interface SpotifyService {

    boolean refreshToken();

    Optional<Track> getTrack(String url);

    Optional<Album> getAlbum(String url);

    Optional<Playlist> getPlaylist(String url);
}
