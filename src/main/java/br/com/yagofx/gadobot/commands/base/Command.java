package br.com.yagofx.gadobot.commands.base;

import net.dv8tion.jda.api.events.Event;

import java.util.List;

public interface Command {

    List<String> getAliases();

    Void run(Event event);

}
