package br.com.yagofx.gadobot.util;

import br.com.yagofx.gadobot.player.AudioTrackWrapper;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

public class SimpleEmbeds {

    public static EmbedBuilder notification(String author, String description) {
        return new EmbedBuilder().clear().setAuthor(author).setDescription(description);
    }

    public static EmbedBuilder songList(List<AudioTrackWrapper> queue, int currentPage, int totalPages) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Lista de músicas:").setFooter(String.format("Página %s/%s", currentPage, totalPages));
        StringBuilder sb = new StringBuilder();

        int trackIndex = 1 + (10 * (currentPage - 1));
        for (int i = 0; i < queue.size(); trackIndex++, i ++)
            sb.append(String.format("%s - %s\n", trackIndex, queue.get(i).getSongName()));

        return embedBuilder.setDescription(sb.toString().trim());
    }

    public static EmbedBuilder about(String botInfo, String description) {
        return new EmbedBuilder().clear().setTitle(botInfo).setDescription(description);
    }

}
