package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
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
    public void run(MessageReceivedEvent messageEvent) {
        messageEvent.getMessage().addReaction(CommonEmojis.THUMBS_UP).queue();
        messageEvent.getGuild().getAudioManager().closeAudioConnection();
        guildService.getGuildAudioPlayer(messageEvent.getGuild()).getScheduler().dispose();
    }

    @Override
    public String helpDescription() {
        return "*Manda o bot embora da sala\n*Tambem limpa a lista de musicas";
    }

    public enum Reason {
        PAUSE,
        STOP,
        QUEUE_END,
        LEFT_ALONE
    }

}
