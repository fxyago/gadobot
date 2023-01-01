package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Shuffle extends AbstractCommand {

    private final GuildService guildService;

    public Shuffle(GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {
        messageEvent.getMessage().addReaction(CommonEmojis.SHUFFLE).queue();
        guildService.getTrackScheduler(messageEvent.getGuild()).shuffle();
    }

    @Override
    public String helpDescription() {
        return "* Embaralha a lista de músicas";
    }
}
