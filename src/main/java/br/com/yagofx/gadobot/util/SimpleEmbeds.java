package br.com.yagofx.gadobot.util;

import net.dv8tion.jda.api.EmbedBuilder;

public class SimpleEmbeds {

    private static final EmbedBuilder EMBED_BUILDER = new EmbedBuilder();

    public static EmbedBuilder notification(String author, String description) {
        return EMBED_BUILDER.clear().setAuthor(author).setDescription(description);
    }

    public static EmbedBuilder songList(String formattedList, int totalPages, int currentPage) {
        return EMBED_BUILDER.clear().setTitle("Lista de músicas:").setDescription(formattedList).setFooter(String.format("Página %s/%s", currentPage, totalPages));
    }

    public static EmbedBuilder about(String botInfo, String description) {
        return EMBED_BUILDER.clear().setTitle(botInfo).setDescription(description);
    }

}
