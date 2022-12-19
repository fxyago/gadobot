package br.com.yagofx.gadobot.service;

import br.com.yagofx.gadobot.player.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public interface GuildService {

    GuildMusicManager getGuildAudioPlayer(Guild guild);

    Character getPrefix(String guildId);

    boolean connectToVoiceChannel(Guild guild, Member member);

}
