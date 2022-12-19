package br.com.yagofx.gadobot.service;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public interface YoutubeService {
    AudioTrack getTrackFrom(String query);
}
