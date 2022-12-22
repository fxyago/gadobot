package br.com.yagofx.gadobot.player;

import br.com.yagofx.gadobot.commands.Repeat;
import br.com.yagofx.gadobot.service.YoutubeService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackScheduler extends AudioEventAdapter {

    @Getter
    AudioTrackWrapper nowPlaying;

    Repeat.LEVEL repeat;

    final YoutubeService youtubeService;

    final AudioPlayer audioPlayer;

    @Getter
    final LinkedBlockingQueue<AudioTrackWrapper> queue;

    @Getter
    final LinkedList<AudioTrackWrapper> history;

    public TrackScheduler(YoutubeService youtubeService, AudioPlayer audioPlayer) {
        this.youtubeService = youtubeService;
        this.audioPlayer = audioPlayer;
        this.history = new LinkedList<>();
        this.queue = new LinkedBlockingQueue<>();
        this.nowPlaying = null;
        this.repeat = Repeat.LEVEL.NONE;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        history.add(0, nowPlaying);
        if (endReason.mayStartNext) nextTrack();
    }

    private void nextTrack() {
        if (repeat == Repeat.LEVEL.SINGLE) audioPlayer.startTrack(nowPlaying.getTrack(), true);

        AudioTrackWrapper nextTrack = queue.poll();

        if (nextTrack == null) return;
        if (nextTrack.getTrack() == null) nextTrack.setTrack(youtubeService.getTrackFrom(nextTrack.getSongName()));

        if (repeat == Repeat.LEVEL.ALL) queue.offer(nextTrack);

        nowPlaying = nextTrack;
        audioPlayer.startTrack(nextTrack.getTrack(), true);
    }

    public synchronized void queue(AudioTrackWrapper newTrack) {
        if (queue.isEmpty()) {
            nowPlaying = newTrack;
            audioPlayer.startTrack(newTrack.getTrack(), true);
        }
        queue.offer(newTrack);
    }

    public synchronized void queueAll(List<AudioTrackWrapper> tracks) {
        System.out.println("Adding " + tracks.size() + " tracks to the queue");
        queue.addAll(tracks);
        if (audioPlayer.getPlayingTrack() == null) nextTrack();
    }

    public void shuffle() {
        List<AudioTrackWrapper> tempList = new ArrayList<>(this.queue);
        Collections.shuffle(tempList);
        queue.clear();
        queue.addAll(tempList);
    }

    public void clearQueue() {
        this.queue.clear();
    }

    public Repeat.LEVEL toggleRepeat(String args) {
        if (args != null) {
            Repeat.LEVEL level = Repeat.LEVEL.valueOf(args);
            this.repeat = level;
        } else {
            switch (repeat) {
                case SINGLE -> repeat = Repeat.LEVEL.ALL;
                case ALL -> repeat = Repeat.LEVEL.NONE;
                case NONE -> repeat = Repeat.LEVEL.SINGLE;
            }
        }
        return repeat;
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        log.error(exception.getLocalizedMessage());
    }

}
