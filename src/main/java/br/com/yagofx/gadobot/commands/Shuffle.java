package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Shuffle extends AbstractCommand {

    private final GuildService guildService;

    public Shuffle(GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public void run(Event event) {
        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
        messageEvent.getMessage().addReaction(CommonEmojis.SHUFFLE).queue();
        guildService.getTrackScheduler(messageEvent.getGuild()).shuffle();
    }

    @Override
    public String helpDescription() {
        return "* Embaralha a lista de músicas";
    }
}
