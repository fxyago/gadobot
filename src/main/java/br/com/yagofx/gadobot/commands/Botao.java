package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.events.Event;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Botao extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("botao");
    }

    @Override
    public Void run(Event event) {
        return null;
    }
}
