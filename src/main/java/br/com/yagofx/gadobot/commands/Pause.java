package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Pause extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("pause");
    }

    @Override
    public void run(Event event) {
        var messageEvent = (MessageReceivedEvent) event;
        messageEvent.getChannel().sendMessage("Pausando!!").queue();
    }

    @Override
    public String helpDescription() {
        return "*Pausa/resume o player de música";
    }
}
