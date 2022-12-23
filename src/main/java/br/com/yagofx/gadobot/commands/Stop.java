package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;

public class Stop extends AbstractCommand {

    private final GuildService guildService;

    public Stop(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public void run(Event event) {
        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
        messageEvent.getMessage().addReaction(CommonEmojis.STOP).queue();
        guildService.getTrackScheduler(messageEvent.getGuild()).stop();
    }

    @Override
    public String helpDescription() {
        return "* Para completamente a execuçao da musica atual";
    }
}
