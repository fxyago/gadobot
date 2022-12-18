package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Pause extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("pause");
    }

    @Override
    public Void run(Event event) {
        var messageEvent = (MessageReceivedEvent) event;
        messageEvent.getChannel().sendMessage("Pausando!!").queue();
        return null;
    }

}
