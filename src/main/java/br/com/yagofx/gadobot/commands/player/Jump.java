package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
import br.com.yagofx.gadobot.util.ParsingUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class Jump extends AbstractCommand {

    private final GuildService guildService;

    public Jump(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public List<String> getAliases() {
        return List.of("jump", "j", "skipto");
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {
        String args = ParsingUtils.extractArgsFrom(messageEvent.getMessage().getContentRaw());
        guildService.getTrackScheduler(messageEvent.getGuild()).jumpTo(Integer.parseInt(args));
        messageEvent.getMessage().addReaction(CommonEmojis.THUMBS_UP).queue();
    }

    @Override
    public String helpDescription() {
        return "*Pula varias musicas da lista ate chegar no numero recebido";
    }

}
