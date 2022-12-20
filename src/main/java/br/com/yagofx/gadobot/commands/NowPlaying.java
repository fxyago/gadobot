package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.events.Event;

import java.util.List;

public class NowPlaying extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("nowplaying", "np");
    }

    @Override
    public Void run(Event event) {
        return null;
    }
}
