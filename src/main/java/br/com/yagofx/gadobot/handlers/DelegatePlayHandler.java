package br.com.yagofx.gadobot.handlers;

import br.com.yagofx.gadobot.player.AudioTrackWrapper;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.service.YoutubeService;
import br.com.yagofx.gadobot.util.ParsingUtils;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class DelegatePlayHandler {

    private final GuildService guildService;
    private final SpotifyHandler spotifyHandler;
    private final YoutubeService youtubeService;

    public DelegatePlayHandler(
            @Qualifier("GuildServiceImpl") GuildService guildService,
            @Qualifier("YoutubeServiceProxy") YoutubeService youtubeService,
            SpotifyHandler spotifyHandler) {
        this.guildService = guildService;
        this.spotifyHandler = spotifyHandler;
        this.youtubeService = youtubeService;
    }

    public void loadAndPlayFrom(MessageReceivedEvent event) {
        String args = ParsingUtils.extractArgsFrom(event.getMessage().getContentRaw());
        final List<AudioTrackWrapper> tracks = new ArrayList<>();

        try {
            processTrackResults(event, args, tracks);
            queue(tracks, event.getGuild(), event.getMember());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void queue(List<AudioTrackWrapper> tracks, Guild guild, Member member) {
        if (!tracks.isEmpty()) {
            guildService.connectToVoiceChannel(guild, member);
            guildService.getGuildAudioPlayer(guild).getScheduler().queueAll(tracks);
        }
    }

    private void processTrackResults(MessageReceivedEvent event, String args, List<AudioTrackWrapper> tracks) throws ExecutionException, InterruptedException {
        if (args.contains("spotify")) {
            tracks.addAll(spotifyHandler.makeWrapperOf(event.getMember(), args));
        } else if (args.contains("playlist")){
            youtubeService.getPlaylistFrom(args).forEach(t -> {
                tracks.add(new AudioTrackWrapper(t, event.getMember(), t.getInfo().title));
            });
        } else {
            AudioTrack audioTrack = youtubeService.getTrackFrom(args);
            tracks.add(new AudioTrackWrapper(audioTrack, event.getMember(), audioTrack.getInfo().title));
        }
    }

}
