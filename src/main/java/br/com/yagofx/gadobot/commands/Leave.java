package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class Leave extends AbstractCommand {

    private final GuildService guildService;

    public Leave(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public List<String> getAliases() {
        return List.of("leave", "exit", "quit", "destroy", "l");
    }

    @Override
    public void run(Event event) {
        var messageEvent = (MessageReceivedEvent) event;
        messageEvent.getGuild().getAudioManager().closeAudioConnection();
        guildService.getGuildAudioPlayer(messageEvent.getGuild()).getScheduler().clearQueue();
    }

    @Override
    public String helpDescription() {
        return "*Manda o bot embora da sala\n*Tambem limpa a lista de musicas";
    }

}
