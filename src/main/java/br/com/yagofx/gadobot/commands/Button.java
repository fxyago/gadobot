package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.events.Event;

import java.util.List;

public class Button extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("botao");
    }

    @Override
    public void run(Event event) {}

    @Override
    public String helpDescription() {
        return "* Nada no momento...";
    }
}
