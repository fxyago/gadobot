package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.player.TrackScheduler;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
import br.com.yagofx.gadobot.util.ParsingUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class Volume extends AbstractCommand {

    private final GuildService guildService;

    public Volume(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public List<String> getAliases() {
        return List.of("volume", "vol");
    }

    @Override
    public void run(Event event) {
        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
        TrackScheduler scheduler = guildService.getTrackScheduler(messageEvent.getGuild());

        try {
            String args = ParsingUtils.extractArgsFrom(messageEvent.getMessage().getContentRaw());
            scheduler.setVolume(Integer.parseInt(args));
            messageEvent.getMessage().addReaction(CommonEmojis.THUMBS_UP).queue();
        } catch (NumberFormatException nfe) {
            messageEvent.getChannel().sendMessage("N consegui mudar o volume, verifique a sintaxe do comando").queue();
        } catch (IndexOutOfBoundsException ioobe) {
            messageEvent.getChannel().sendMessageEmbeds(new EmbedBuilder()
                    .setAuthor("Volume atual:")
                    .setDescription(scheduler.getVolume().toString())
                    .build()).queue();
        }
    }

    @Override
    public String helpDescription() {
        return "* Muda o volume do player do bot\n* Volumes acima de 100 podem causar distorções no áudio";
    }
}
