package br.com.yagofx.gadobot.commands.base;

import net.dv8tion.jda.api.events.Event;

import java.util.List;

public interface Command {

    default List<String> getAliases() {
        return List.of(this.getClass().getSimpleName().toLowerCase());
    }

    Void run(Event event);

}
