package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
import br.com.yagofx.gadobot.util.ParsingUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Repeat extends AbstractCommand {

    private final GuildService guildService;

    public Repeat(GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public List<String> getAliases() {
        return List.of("repeat", "loop");
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {
        String args = null;
        try {
            args = ParsingUtils.extractArgsFrom(messageEvent.getMessage().getContentRaw());
        } catch (IndexOutOfBoundsException ignored) {}

        LEVEL level = guildService.getTrackScheduler(messageEvent.getGuild()).toggleRepeat(args);
        messageEvent.getMessage().addReaction(CommonEmojis.REPEAT).queue();
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
