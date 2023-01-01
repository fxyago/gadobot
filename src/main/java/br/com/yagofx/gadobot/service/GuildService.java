package br.com.yagofx.gadobot.service;

import br.com.yagofx.gadobot.player.GuildMusicManager;
import br.com.yagofx.gadobot.player.TrackScheduler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public interface GuildService {

    GuildMusicManager getGuildAudioPlayer(Guild guild);

    Character getPrefix(String guildId);

    boolean connectToVoiceChannel(Guild guild, Member member);

    void setPrefix(String guildId, Character newPrefix);

    TrackScheduler getTrackScheduler(Guild guild);

    void disconnect(Guild guild);

}
