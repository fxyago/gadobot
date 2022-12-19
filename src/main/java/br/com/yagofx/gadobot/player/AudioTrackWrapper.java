package br.com.yagofx.gadobot.player;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.entities.Member;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AudioTrackWrapper {

    AudioTrack track;

    Member member;

    String songName;

    public AudioTrackWrapper(Member member, String songName) {
        this.member = member;
        this.songName = songName;
    }
}
