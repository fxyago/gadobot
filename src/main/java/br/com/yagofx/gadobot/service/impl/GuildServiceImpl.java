package br.com.yagofx.gadobot.service.impl;

import br.com.yagofx.gadobot.entity.GuildPreferences;
import br.com.yagofx.gadobot.player.GuildMusicManager;
import br.com.yagofx.gadobot.player.TrackScheduler;
import br.com.yagofx.gadobot.repository.GuildPreferencesRepository;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.service.YoutubeService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service("GuildServiceImpl")
public class GuildServiceImpl implements GuildService {

    private final AudioPlayerManager playerManager;
    private final GuildPreferencesRepository repository;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final YoutubeService youtubeService;

    public GuildServiceImpl(
            @Qualifier("YoutubeServiceProxy")
            YoutubeService youtubeService,
            AudioPlayerManager playerManager,
            GuildPreferencesRepository repository) {
        this.playerManager = playerManager;
        this.repository = repository;
        this.youtubeService = youtubeService;
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
                audioManager.setSelfDeafened(true);
                audioManager.openAudioConnection(vc);
                return true;
            }
        }
        return false;
    }

    @Override
    public void setPrefix(String guildId, Character newPrefix) {
        Optional<GuildPreferences> optional = repository.findById(guildId);
        GuildPreferences prefs = optional.orElseGet(() -> new GuildPreferences(guildId));
        prefs.setPrefix(newPrefix);
        repository.save(prefs);
    }

    @Override
    public TrackScheduler getTrackScheduler(Guild guild) {
        return this.getGuildAudioPlayer(guild).getScheduler();
    }

    private GuildMusicManager getMusicManager(Long guildId) {
        GuildMusicManager musicManager = musicManagers.get(guildId);
        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager, youtubeService);
            musicManagers.put(guildId, musicManager);
        }
        return musicManager;
    }

    @Override
    public void disconnect(Guild guild) {
        var audioManager = guild.getAudioManager();
        if (audioManager.isConnected()) audioManager.closeAudioConnection();
    }

}
