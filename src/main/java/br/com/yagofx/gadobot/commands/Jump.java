package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.events.Event;

import java.util.List;

public class Jump extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("jump", "skipto");
    }

    @Override
    public void run(Event event) {

    }

    @Override
    public String helpDescription() {
        return "*Pula varias musicas da lista ate chegar no numero recebido";
    }

}
