package br.com.yagofx.gadobot.service;

import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.concurrent.CompletableFuture;

public interface SpotifyService {

    void refreshToken();

    CompletableFuture<Track> getTrack(String url);

    CompletableFuture<Album> getAlbum(String url);

    CompletableFuture<Playlist> getPlaylist(String url);
}
