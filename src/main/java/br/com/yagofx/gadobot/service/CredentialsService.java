package br.com.yagofx.gadobot.service;

import br.com.yagofx.gadobot.entity.DiscordCredentials;
import br.com.yagofx.gadobot.entity.SpotifyCredentials;

public interface CredentialsService {

    <T> T get(Class<T> t);

    void save(SpotifyCredentials credentials);

    void save(DiscordCredentials credentials);

}
