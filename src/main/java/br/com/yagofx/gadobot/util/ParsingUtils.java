package br.com.yagofx.gadobot.util;

public class ParsingUtils {

    public static String parseCommand(String message) {
        return message.substring(1).split(" ")[0];
    }

    public static String extractSpotifyIdFrom(String url) {
        return url.split("track/|album/|playlist/")[1].split("\\?si")[0];
    }

}
