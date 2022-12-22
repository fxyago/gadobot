package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.ParsingUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Repeat extends AbstractCommand {

    private final GuildService guildService;

    public Repeat(GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public void run(Event event) {
        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
        String args = null;
        try {
            args = ParsingUtils.extractArgsFrom(messageEvent.getMessage().getContentRaw());
        } catch (IndexOutOfBoundsException ignored) {}

        LEVEL level = guildService.getTrackScheduler(messageEvent.getGuild()).toggleRepeat(args);
        messageEvent.getMessage().addReaction(Emoji.fromUnicode("U+1F501")).queue();
        messageEvent.getChannel().sendMessageEmbeds(
                new EmbedBuilder()
                    .setTitle("Repeat alterado para: " + level.name())
                    .build()).queue();
    }

    @Override
    public String helpDescription() {
        return "* Alterna entre os modos de repeat:\n* Single: repete a musica atual\n* All: repete a playlist inteira\n* None: Desativa o repeat";
    }

    public enum LEVEL {
        SINGLE, ALL, NONE
    }

}
