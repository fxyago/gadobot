package br.com.yagofx.gadobot.handlers;

import br.com.yagofx.gadobot.player.AudioTrackWrapper;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.service.YoutubeService;
import br.com.yagofx.gadobot.util.ParsingUtils;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.Event;
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
            queue(tracks, event);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void queue(List<AudioTrackWrapper> tracks, Event event) {
        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;

        if (!tracks.isEmpty()) {
            if (guildService.connectToVoiceChannel(messageEvent.getGuild(), messageEvent.getMember())) {
                messageEvent.getChannel().sendMessageEmbeds(formattedEmbedTitle(tracks)).queue();
                var scheduler = guildService.getGuildAudioPlayer(messageEvent.getGuild()).getScheduler();
                scheduler.queueAll(tracks);
                scheduler.setCallback((reason) -> {

                    String message = switch (reason) {
                        case PAUSE -> "Tomei uma pausada por muuuuuito tempo, flw";
                        case STOP -> "Tomei uma stopada por muuuuuito tempo, vlw fml";
                        case QUEUE_END -> "Acabou a muuuusica, to vazando glr";
                        case LEFT_ALONE -> "Fui embora pois me deixaram sozinho na sala, muuuuuu";
                    };

                    guildService.disconnect(messageEvent.getGuild());
                    messageEvent.getChannel().sendMessageEmbeds(new EmbedBuilder().setDescription(message).build()).queue();
                    return null;
                });
            }
        }
    }

    private MessageEmbed formattedEmbedTitle(List<AudioTrackWrapper> tracks) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setAuthor("Adicionando à fila:");

        if (tracks.size() == 1)
            if (tracks.get(0).getTrack() != null)
                embedBuilder.setTitle(tracks.get(0).getTrack().getInfo().title, tracks.get(0).getTrack().getInfo().uri);
            else
                embedBuilder.setTitle(tracks.get(0).getSongName());
        else
            embedBuilder.setTitle(tracks.size() + " músicas");

        return embedBuilder.build();
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
