package br.com.yagofx.gadobot.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.LinkedBlockingQueue;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackScheduler extends AudioEventAdapter {

    private AudioPlayerManager playerManager;

    private final AudioPlayer audioPlayer;
    private final LinkedBlockingQueue<AudioTrackWrapper> queue;

    public TrackScheduler(AudioPlayer audioPlayer, LinkedBlockingQueue<AudioTrackWrapper> queue) {
        this.audioPlayer = audioPlayer;
        this.queue = queue;
    }

    public void queue(AudioTrackWrapper track) {
        queue.offer(track);
    }

    public void clearQueue() {}

}
