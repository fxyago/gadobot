package br.com.yagofx.gadobot.service.impl;

import br.com.yagofx.gadobot.entity.DiscordCredentials;
import br.com.yagofx.gadobot.entity.SpotifyCredentials;
import br.com.yagofx.gadobot.repository.DiscordCredentialsRepository;
import br.com.yagofx.gadobot.repository.SpotifyCredentialsRepository;
import br.com.yagofx.gadobot.service.CredentialsService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service("CredentialsServiceImpl")
public class CredentialsServiceImpl implements CredentialsService {

    private final DiscordCredentialsRepository discordRepository;
    private final SpotifyCredentialsRepository spotifyRepository;

    public CredentialsServiceImpl(
            SpotifyCredentialsRepository spotifyRepository,
            DiscordCredentialsRepository discordRepository) {

        this.spotifyRepository = spotifyRepository;
        this.discordRepository = discordRepository;
    }

    @Override
    public <T> T get(Class<T> t) {
        if (t.equals(SpotifyCredentials.class)) return (T) getSpotifyCredentials();
        if (t.equals(DiscordCredentials.class)) return (T) getDiscordCredentials();
        throw new NoSuchElementException("Nenhuma credencial encontrada para esta classe");
    }

    @Override
    public void saveCredentials(SpotifyCredentials credentials) {
        spotifyRepository.save(credentials);
    }

    @Override
    public void saveCredentials(DiscordCredentials credentials) {
        discordRepository.save(credentials);
    }

    public SpotifyCredentials getSpotifyCredentials() {
        return spotifyRepository.findFirst();
    }

    public DiscordCredentials getDiscordCredentials() {
        return discordRepository.findFirst();
    }

}
