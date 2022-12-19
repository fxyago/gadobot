package br.com.yagofx.gadobot.service.proxy;

import br.com.yagofx.gadobot.service.YoutubeService;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class YoutubeServiceProxy implements YoutubeService {

    private final AudioPlayerManager playerManager;

    public YoutubeServiceProxy(AudioPlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public synchronized AudioTrack getTrackFrom(String query) {
        final AudioTrack[] trackFound = {null};

        if (!query.contains(".com")) query = "ytsearch:" + query;

        log.info(query);

        try {
            playerManager.loadItem(query, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    trackFound[0] = track;
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    trackFound[0] = playlist.getTracks().get(0);
                }

                @Override
                public void noMatches() {
                    throw new NoSuchElementException("No matches");
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    throw new RuntimeException(exception.getLocalizedMessage());
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new NoSuchElementException("An error has ocurred");
        }
        return trackFound[0];
    }

    @Override
    public synchronized List<AudioTrack> getPlaylistFrom(String url) {
        final List<AudioTrack> tracks = new ArrayList<>();
        try {
            playerManager.loadItem(url, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    throw new RuntimeException("Now allowed");
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    tracks.addAll(playlist.getTracks());
                }

                @Override
                public void noMatches() {
                    throw new NoSuchElementException("Nenhuma musica encontrada");
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    throw exception;
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("An error has ocurred");
        }
        return tracks;
    }

}
