package br.com.yagofx.gadobot.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.LinkedBlockingQueue;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackScheduler extends AudioEventAdapter {

    @Setter
    private AudioPlayerManager playerManager;

    private final AudioPlayer audioPlayer;
    private final LinkedBlockingQueue<AudioTrackWrapper> queue;

    public TrackScheduler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrackWrapper track) {
        queue.offer(track);
    }

    public void clearQueue() {}

}
