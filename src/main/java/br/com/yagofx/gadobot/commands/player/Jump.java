package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Jump extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("jump", "skipto");
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {

    }

    @Override
    public String helpDescription() {
        return "*Pula varias musicas da lista ate chegar no numero recebido";
    }

}
