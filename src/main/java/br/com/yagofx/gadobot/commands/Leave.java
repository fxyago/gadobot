package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Leave extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("leave", "exit", "quit", "destroy", "l");
    }

    @Override
    public Void run(Event event) {
        var messageEvent = (MessageReceivedEvent) event;
        messageEvent.getGuild().getAudioManager().closeAudioConnection();
        return null;
    }

}
