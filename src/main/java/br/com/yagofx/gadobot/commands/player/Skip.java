package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
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
    public void run(MessageReceivedEvent messageEvent) {
        messageEvent.getMessage().addReaction(CommonEmojis.NEXT_TRACK).queue();
        guildService.getTrackScheduler(messageEvent.getGuild()).nextTrack();
    }

    @Override
    public String helpDescription() {
        return "* Pula para a próxima música da fila\n* Para o player caso a fila esteja vazia";
    }

}
