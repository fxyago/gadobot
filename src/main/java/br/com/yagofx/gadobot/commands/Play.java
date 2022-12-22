package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.handlers.DelegatePlayHandler;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Play extends AbstractCommand {

    private final DelegatePlayHandler handler;

    public Play(DelegatePlayHandler handler) {
        this.handler = handler;
    }

    @Override
    public List<String> getAliases() {
        return List.of("play", "p");
    }

    @Override
    public void run(Event event) {
        var messageEvent = (MessageReceivedEvent) event;
        handler.loadAndPlayFrom(messageEvent);
    }

    @Override
    public String helpDescription() {
        return "*Adiciona músicas a fila\n*Aceita links ou parametros para busca\n*Links podem ser do YouTube ou Spotify\n*Tambem aceita links de albums ou playlists";
    }

}
