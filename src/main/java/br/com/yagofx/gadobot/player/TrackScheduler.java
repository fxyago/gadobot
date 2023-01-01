package br.com.yagofx.gadobot.player;

import br.com.yagofx.gadobot.commands.player.Leave;
import br.com.yagofx.gadobot.commands.player.Repeat;
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
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

import static br.com.yagofx.gadobot.commands.player.Leave.Reason.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackScheduler extends AudioEventAdapter {

    @Getter
    AudioTrackWrapper nowPlaying;

    Repeat.LEVEL repeat;

    final YoutubeService youtubeService;

    final AudioPlayer audioPlayer;

    final LinkedBlockingQueue<AudioTrackWrapper> queue;
    final LinkedBlockingDeque<AudioTrackWrapper> history;

    final ScheduledExecutorService executor;
    Function<Leave.Reason, Void> callback;
    Future<?> disconnectTask;

    public TrackScheduler(YoutubeService youtubeService, AudioPlayer audioPlayer) {
        this.youtubeService = youtubeService;
        this.audioPlayer = audioPlayer;
        this.history = new LinkedBlockingDeque<>();
        this.queue = new LinkedBlockingQueue<>();
        this.nowPlaying = null;
        this.repeat = Repeat.LEVEL.NONE;
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.disconnectTask = null;
        this.callback = null;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (nowPlaying != null)
            history.offerFirst(nowPlaying);

        if (repeat == Repeat.LEVEL.SINGLE) {
            nowPlaying.setTrack(nowPlaying.getTrack().makeClone());
            audioPlayer.startTrack(nowPlaying.getTrack(), false);
        } else if (endReason.mayStartNext) nextTrack();
    }

    public void nextTrack() {
        AudioTrackWrapper nextTrack = queue.poll();

        nowPlaying = nextTrack;
        if (nextTrack == null)  {
            audioPlayer.stopTrack();
            startTimer(QUEUE_END);
            return;
        }
        if (nextTrack.getTrack() == null) nextTrack.setTrack(youtubeService.getTrackFrom(nextTrack.getSongName()));
        if (repeat == Repeat.LEVEL.ALL) queue.offer(nextTrack);

        audioPlayer.startTrack(nextTrack.getTrack(), false);
    }

    public synchronized void queue(AudioTrackWrapper newTrack) {
        stopTimer();
        if (queue.isEmpty()) {
            nowPlaying = newTrack;
            audioPlayer.startTrack(newTrack.getTrack(), true);
        }
        queue.offer(newTrack);
    }

    public synchronized void queueAll(List<AudioTrackWrapper> tracks) {
        log.debug("Adding " + tracks.size() + " tracks to the queue");
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
        queue.clear();
    }

    public Repeat.LEVEL toggleRepeat(String args) {
        if (args != null) {
            this.repeat = Repeat.LEVEL.valueOf(args);
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

    public void stop() {
        audioPlayer.stopTrack();
        startTimer(STOP);
    }

    public void togglePause() {
        audioPlayer.setPaused(!audioPlayer.isPaused());
    }

    public boolean isPaused() {
        return audioPlayer.isPaused();
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        startTimer(PAUSE);
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        stopTimer();
    }

    public void setVolume(int volume) {
        this.audioPlayer.setVolume(volume);
    }

    public AudioTrackWrapper move(Integer fromPosition, Integer toPosition) {
        ArrayList<AudioTrackWrapper> tracks = new ArrayList<>();
        queue.drainTo(tracks);
        AudioTrackWrapper trackToMove = tracks.get(fromPosition - 1);
        tracks.remove(trackToMove);
        tracks.add(toPosition - 1, trackToMove);
        queue.addAll(tracks);
        return trackToMove;
    }

    public void dispose() {
        this.queue.clear();
        this.audioPlayer.stopTrack();
        this.nowPlaying = null;
    }

    public void setCallback(Function<Leave.Reason, Void> callback) {
        this.callback = callback;
    }

    public void timerOverride(Leave.Reason reason) {
        stopTimer();
        if (reason == null) return;

        startTimer(reason);
    }

    private void startTimer(Leave.Reason reason) {
        this.disconnectTask = executor.schedule(() -> callback.apply(reason), 5, TimeUnit.MINUTES);
    }

    private void stopTimer() {
        if (disconnectTask != null && !disconnectTask.isCancelled()) {
            disconnectTask.cancel(false);
        }
    }

    public Integer getVolume() {
        return audioPlayer.getVolume();
    }

    public List<AudioTrackWrapper> getQueue() {
        return List.copyOf(this.queue);
    }
}
