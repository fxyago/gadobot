package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.handlers.DelegatePlayHandler;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Play extends AbstractCommand {

    private final DelegatePlayHandler handler;

    public Play(DelegatePlayHandler handler) {
        this.handler = handler;
    }

    @Override
    public List<String> getAliases() {
        return List.of("play", "p");
    }

    @Override
    public Void run(Event event) {
        var messageEvent = (MessageReceivedEvent) event;
        handler.loadAndPlayFrom(messageEvent);
        messageEvent.getChannel().sendMessage("Playando!!").queue();
        return null;
    }

}
