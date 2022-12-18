package br.com.yagofx.gadobot.service.proxy;

import br.com.yagofx.gadobot.service.SpotifyService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Optional;

@Service
public class SpotifyServiceProxy implements SpotifyService {

    private final SpotifyApi spotifyApi;

    public SpotifyServiceProxy(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    @Override
    public boolean refreshToken() {
        return false;
    }

    @Override
    public Optional<Track> getTrack(String url) {
        return Optional.empty();
    }

    @Override
    public Optional<Album> getAlbum(String url) {
        return Optional.empty();
    }

    @Override
    public Optional<Playlist> getPlaylist(String url) {
        return Optional.empty();
    }

}
