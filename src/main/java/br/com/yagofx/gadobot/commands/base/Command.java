package br.com.yagofx.gadobot.commands.base;

import br.com.yagofx.gadobot.listener.CommandListener;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.events.Event;

import java.util.List;

public interface Command {

    default String name() {
        return this.getClass().getSimpleName();
    }

    default List<String> getAliases() {
        return List.of(this.getClass().getSimpleName().toLowerCase());
    }

    void run(Event event);

    String helpDescription();

    @PostConstruct
    default void subscribe() {
        CommandListener.addCommand(this);
    }

}
