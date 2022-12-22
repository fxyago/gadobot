package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.events.Event;

import java.util.List;

public class Queue extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("queue", "q", "tracklist");
    }

    @Override
    public void run(Event event) {
    }

    @Override
    public String helpDescription() {
        return "*Mostra a fila de músicas em uma lista";
    }
}
