package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Button extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("botao");
    }

    @Override
    public Void run(Event event) {
        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
        messageEvent.getChannel().sendMessage("Muuuu??").queue();
        return null;
    }
}
