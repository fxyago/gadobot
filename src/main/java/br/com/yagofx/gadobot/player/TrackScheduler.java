package br.com.yagofx.gadobot.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
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

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) nextTrack();
    }

    private void nextTrack() {
        AudioTrackWrapper nextTrack = queue.peek();
        if (nextTrack != null && nextTrack.getTrack() != null) {
            queue.remove(nextTrack);
            audioPlayer.startTrack(nextTrack.getTrack(), false);
        }
    }

    public synchronized void queue(AudioTrackWrapper track) {
        queue.offer(track);
    }

    public synchronized void queueAll(List<AudioTrackWrapper> tracks) {
        System.out.println("Adding " + tracks.toString());
        queue.addAll(tracks);
        System.out.println(audioPlayer.getPlayingTrack());
        if (audioPlayer.getPlayingTrack() == null) nextTrack();
    }

    public void clearQueue() {
        this.queue.clear();
    }

    public void nowPlaying() {
        String title = this.audioPlayer.getPlayingTrack().getInfo().title;
        System.out.println("Ta tocando esse treco aqui ó: " + title);
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        log.error(exception.getLocalizedMessage());
    }
}
