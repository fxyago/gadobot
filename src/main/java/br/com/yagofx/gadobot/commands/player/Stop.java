package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
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
    public void run(MessageReceivedEvent messageEvent) {
        messageEvent.getMessage().addReaction(CommonEmojis.STOP).queue();
        guildService.getTrackScheduler(messageEvent.getGuild()).stop();
    }

    @Override
    public String helpDescription() {
        return "* Para completamente a execuçao da musica atual";
    }
}
