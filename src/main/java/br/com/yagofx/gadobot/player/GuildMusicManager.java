package br.com.yagofx.gadobot.player;

import br.com.yagofx.gadobot.service.YoutubeService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.Data;

@Data
public class GuildMusicManager {

    private final AudioPlayer player;
    private final TrackScheduler scheduler;

    public GuildMusicManager(AudioPlayerManager manager, YoutubeService youtubeService) {
        this.player = manager.createPlayer();
        this.scheduler = new TrackScheduler(youtubeService, player);
        player.addListener(scheduler);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }

}
