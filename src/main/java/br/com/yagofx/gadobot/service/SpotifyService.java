package br.com.yagofx.gadobot.service;

import java.util.List;

public interface SpotifyService {

    void refreshToken();

    String getNameFrom(String url);

    List<String> getNamesFrom(String url);

}
