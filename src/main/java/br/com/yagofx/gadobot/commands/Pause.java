package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class Pause extends AbstractCommand {

    private final GuildService guildService;

    public Pause(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public List<String> getAliases() {
        return List.of("pause");
    }

    @Override
    public void run(Event event) {
        var messageEvent = (MessageReceivedEvent) event;
        var scheduler = guildService.getTrackScheduler(messageEvent.getGuild());
        boolean paused = scheduler.isPaused();
        messageEvent.getMessage().addReaction(paused ? CommonEmojis.PLAY : CommonEmojis.PAUSE).queue();
        scheduler.togglePause();
    }

    @Override
    public String helpDescription() {
        return "* Pausa/resume o player de música";
    }
}
