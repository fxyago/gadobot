package br.com.yagofx.gadobot.handlers;

import br.com.yagofx.gadobot.player.AudioTrackWrapper;
import br.com.yagofx.gadobot.service.SpotifyService;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class SpotifyHandler {

    private final SpotifyService spotifyService;

    public SpotifyHandler(
            @Qualifier("SpotifyServiceProxy")
            SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    public List<AudioTrackWrapper> makeWrapperOf(Member member, String url) throws ExecutionException, InterruptedException {
        List<AudioTrackWrapper> wrappedTracks = new ArrayList<>();
        List<String> tracks = new ArrayList<>();

        if (url.contains("playlist") || url.contains("album")) tracks = spotifyService.getNamesFrom(url);
        else if (url.contains("track")) tracks.add(spotifyService.getNameFrom(url));

        tracks.forEach(t -> wrappedTracks.add(new AudioTrackWrapper(member, t)));
        return wrappedTracks;
    }

}