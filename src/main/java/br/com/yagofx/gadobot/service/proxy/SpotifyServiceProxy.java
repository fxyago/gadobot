package br.com.yagofx.gadobot.service.proxy;

import br.com.yagofx.gadobot.entity.SpotifyCredentials;
import br.com.yagofx.gadobot.service.CredentialsService;
import br.com.yagofx.gadobot.service.SpotifyService;
import br.com.yagofx.gadobot.util.ParsingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
    public String getNameFrom(String url) {
        try {
            return ParsingUtils.formatSpotifyItem(spotifyApi.getTrack(ParsingUtils.extractSpotifyIdFrom(url)).build().execute());
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException("Vish!! algo deu muito errado aqui");
    }

    @Override
    public List<String> getNamesFrom(String url) {
        try {
            if (url.contains("album"))
                return extractAlbumItemNamesFrom(url);

            if (url.contains("playlist"))
                return extractPlaylistItemNamesFrom(url);

        } catch (IOException | ParseException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException("Vish!! algo deu muito errado");
    }

    private List<String> extractPlaylistItemNamesFrom(String url) throws IOException, ParseException, SpotifyWebApiException {
        List<String> songNames = new ArrayList<>();
        String playlistId = ParsingUtils.extractSpotifyIdFrom(url);
        Playlist playlist = spotifyApi.getPlaylist(playlistId).build().execute();
        Integer total = playlist.getTracks().getTotal();

        for (int i = 0; i < total; i+=100) {
            Paging<PlaylistTrack> execute = spotifyApi.getPlaylistsItems(playlistId).offset(i).build().execute();
            Arrays.stream(execute.getItems()).forEach(t -> songNames.add(ParsingUtils.formatSpotifyItem((Track) t.getTrack())));
        }
        return songNames;
    }

    private List<String> extractAlbumItemNamesFrom(String url) throws IOException, ParseException, SpotifyWebApiException {
        Paging<TrackSimplified> trackPaging = spotifyApi.getAlbumsTracks(ParsingUtils.extractSpotifyIdFrom(url)).build().execute();
        return Arrays.stream(trackPaging.getItems()).map(ParsingUtils::formatSpotifyItem).collect(Collectors.toList());
    }

}
