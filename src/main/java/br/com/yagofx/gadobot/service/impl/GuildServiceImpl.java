package br.com.yagofx.gadobot.service.impl;

import br.com.yagofx.gadobot.entity.GuildPreferences;
import br.com.yagofx.gadobot.player.GuildMusicManager;
import br.com.yagofx.gadobot.repository.GuildPreferencesRepository;
import br.com.yagofx.gadobot.service.GuildService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service("GuildServiceImpl")
public class GuildServiceImpl implements GuildService {

    private final AudioPlayerManager playerManager;
    private final GuildPreferencesRepository repository;
    private final Map<Long, GuildMusicManager> musicManagers;

    public GuildServiceImpl(
            AudioPlayerManager playerManager,
            GuildPreferencesRepository repository) {
        this.playerManager = playerManager;
        this.repository = repository;
        this.musicManagers = new HashMap<>();
    }

    @Override
    public GuildMusicManager getGuildAudioPlayer(Guild guild) {
        Long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = getMusicManager(guildId);
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }

    @Override
    public Character getPrefix(String guildId) {
        Optional<GuildPreferences> preferences = repository.findById(guildId);
        if (preferences.isPresent()) return preferences.get().getPrefix();

        GuildPreferences newPreferences = new GuildPreferences(guildId);
        repository.save(newPreferences);
        return newPreferences.getPrefix();
    }

    @Override
    public boolean connectToVoiceChannel(Guild guild, Member member) {
        var audioManager = guild.getAudioManager();
        if (audioManager.isConnected()) return true;

        for (VoiceChannel vc : audioManager.getGuild().getVoiceChannels()) {
            if (vc.getMembers().contains(member)) {
                audioManager.openAudioConnection(vc);
                return true;
            }
        }
        return false;
    }

    private GuildMusicManager getMusicManager(Long guildId) {
        GuildMusicManager musicManager = musicManagers.get(guildId);
        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManager.getScheduler().setPlayerManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }
        return musicManager;
    }

}
