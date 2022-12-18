package br.com.yagofx.gadobot.beans;

import br.com.yagofx.gadobot.entity.DiscordCredentials;
import br.com.yagofx.gadobot.service.CredentialsService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import se.michaelthelin.spotify.SpotifyApi;

@Slf4j
@DependsOn("CredentialsServiceImpl")
@Configuration
public class BeanFactory {

    private final CredentialsService credentialsService;

    public BeanFactory(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @Bean
    public JDA buildJda() {
        log.debug("Instanciando JDA...");
        DiscordCredentials creds = credentialsService.get(DiscordCredentials.class);
        return JDABuilder.createDefault(creds.getToken())
                .setActivity(Activity.listening("seus pensamentos"))
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.EMOJI, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                .addEventListeners()
                .build();
    }

    @Bean
    public SpotifyApi spotifyApi() {
        log.debug("Instanciando Spotify API...");
        return SpotifyApi.builder()
                .setAccessToken("")
                .setClientId("")
                .setClientSecret("")
                .build();
    }

}
