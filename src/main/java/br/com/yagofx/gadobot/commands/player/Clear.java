package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("ClearCommand")
public class Clear extends AbstractCommand {

    private final GuildService guildService;

    public Clear(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {
        messageEvent.getMessage().addReaction(CommonEmojis.THUMBS_UP).queue();
        guildService.getTrackScheduler(messageEvent.getGuild()).clearQueue();
    }

    @Override
    public String helpDescription() {
        return "* Limpa a fila de músicas subsequentes sem parar a musica atual";
    }

}
