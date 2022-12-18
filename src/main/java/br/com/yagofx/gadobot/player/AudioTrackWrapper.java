package br.com.yagofx.gadobot.player;


import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Member;

@Data(staticConstructor = "wrap")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AudioTrackWrapper {

    AudioTrack track;

    Member member;

    String songName;

}
