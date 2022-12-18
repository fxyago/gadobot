package br.com.yagofx.gadobot.service.proxy;

import br.com.yagofx.gadobot.entity.SpotifyCredentials;
import br.com.yagofx.gadobot.service.CredentialsService;
import br.com.yagofx.gadobot.service.SpotifyService;
import br.com.yagofx.gadobot.util.ParsingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service("SpotifyServiceProxy")
@DependsOn({"CredentialsServiceImpl", "spotifyApi"})
public class SpotifyServiceProxy implements SpotifyService {

    private final CredentialsService credentialsService;
    private final SpotifyApi spotifyApi;

    public SpotifyServiceProxy(
            @Qualifier("CredentialsServiceImpl")
            CredentialsService credentialsService,
            SpotifyApi spotifyApi) {
        this.credentialsService = credentialsService;
        this.spotifyApi = spotifyApi;
    }

    @Override
    public void refreshToken() {
        try {
            SpotifyCredentials credentials = credentialsService.get(SpotifyCredentials.class);

            final AuthorizationCodeCredentials newCredentials = spotifyApi.authorizationCodeRefresh().build().executeAsync().get();

            credentials.setAccessToken(newCredentials.getAccessToken());

            credentialsService.save(credentials);

            log.info("Token do spotify atualizado com sucesso");
            log.info("Expira em: {}s", newCredentials.getExpiresIn());
        } catch (ExecutionException | InterruptedException e) {
            log.error("Ocorreu um erro ao atualizar os tokens do Spotify");
            e.printStackTrace();
        }
    }

    @Override
    public CompletableFuture<Track> getTrack(String url) {
        return spotifyApi.getTrack(ParsingUtils.extractSpotifyIdFrom(url)).build().executeAsync();
    }

    @Override
    public CompletableFuture<Album> getAlbum(String url) {
        return spotifyApi.getAlbum(ParsingUtils.extractSpotifyIdFrom(url)).build().executeAsync();
    }

    @Override
    public CompletableFuture<Playlist> getPlaylist(String url) {
        return spotifyApi.getPlaylist(ParsingUtils.extractSpotifyIdFrom(url)).build().executeAsync();
    }

}
