package br.com.yagofx.gadobot.util;

import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import java.util.List;

public class ParsingUtils {

    public static String extractCommandFrom(String message) {
        return message.substring(1).split(" ")[0];
    }

    public static String extractArgsFrom(String message) {
        return message.split(" ", 2)[1];
    }

    public static String extractSpotifyIdFrom(String url) {
        return url.split("track/|album/|playlist/")[1].split("\\?si")[0];
    }

    public static String formatSpotifyItem(Track track) {
        return String.format("%s - %s", track.getArtists()[0], track.getName());
    }

    public static String formatSpotifyItem(TrackSimplified track) {
        return String.format("%s - %s", track.getArtists()[0], track.getName());
    }

    public static String formatCommandAliases(List<String> aliases) {
        StringBuilder sb = new StringBuilder();
        aliases.forEach(a -> sb.append(String.format("`%s` ", a)));
        return sb.toString().trim();
    }

}
