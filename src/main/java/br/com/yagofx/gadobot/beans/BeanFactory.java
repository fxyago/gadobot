package br.com.yagofx.gadobot.beans;

import br.com.yagofx.gadobot.entity.DiscordCredentials;
import br.com.yagofx.gadobot.entity.SpotifyCredentials;
import br.com.yagofx.gadobot.listener.CommandListener;
import br.com.yagofx.gadobot.listener.GuildListener;
import br.com.yagofx.gadobot.service.CredentialsService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.EnumSet;

@Slf4j
@Configuration
public class BeanFactory {

    private final CredentialsService credentialsService;

    public BeanFactory(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @Bean
    public JDA buildJda(
            CommandListener commandListener,
            GuildListener guildListener
    ) {
        log.debug("Instanciando JDA...");

        DiscordCredentials creds = credentialsService.get(DiscordCredentials.class);

        return JDABuilder.createDefault(creds.getToken())
                .setActivity(Activity.listening("seus pensamentos"))
                .setEnabledIntents(EnumSet.allOf(GatewayIntent.class))
                .addEventListeners(commandListener, guildListener)
                .build();
    }

    @Bean
    public SpotifyApi spotifyApi() {
        log.debug("Instanciando Spotify API...");
        SpotifyCredentials creds = credentialsService.get(SpotifyCredentials.class);

        return SpotifyApi.builder()
                .setAccessToken(creds.getAccessToken())
                .setClientId(creds.getClientId())
                .setClientSecret(creds.getClientSecret())
                .setRefreshToken(creds.getRefreshToken())
                .build();
    }

    @Bean
    public AudioPlayerManager playerManager() {
        return new DefaultAudioPlayerManager();
    }

}
