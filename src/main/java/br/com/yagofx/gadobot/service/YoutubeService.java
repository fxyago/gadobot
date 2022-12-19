package br.com.yagofx.gadobot.service;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.List;

public interface YoutubeService {
    AudioTrack getTrackFrom(String query);

    List<AudioTrack> getPlaylistFrom(String url);
}
