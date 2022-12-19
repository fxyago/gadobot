package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class Summon extends AbstractCommand {

    private final GuildService service;

    public Summon(@Qualifier("GuildServiceImpl") GuildService service) {
        this.service = service;
    }

    @Override
    public List<String> getAliases() {
        return List.of("summon", "join");
    }

    @Override
    public Void run(Event event) {
        var messageEvent = (MessageReceivedEvent) event;

        if (!service.connectToVoiceChannel(messageEvent.getGuild(), messageEvent.getMember()))
            messageEvent.getChannel().sendMessage("Entra numa sala primeiro krl").queue();

        return null;
    }



}
