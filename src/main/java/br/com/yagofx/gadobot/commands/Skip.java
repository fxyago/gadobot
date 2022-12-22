package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class Skip extends AbstractCommand {

    private final GuildService guildService;

    public Skip(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public List<String> getAliases() {
        return List.of("skip", "next", "jump", "n", "s");
    }

    @Override
    public void run(Event event) {
        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
        messageEvent.getMessage().addReaction(Emoji.fromUnicode("U+23ED")).queue();
        guildService.getTrackScheduler(messageEvent.getGuild()).nextTrack();
    }

    @Override
    public String helpDescription() {
        return "* Pula para a próxima música da fila\n* Para o player caso a fila esteja vazia";
    }

}
