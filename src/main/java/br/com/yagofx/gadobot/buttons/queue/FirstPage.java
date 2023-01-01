package br.com.yagofx.gadobot.buttons.queue;

import br.com.yagofx.gadobot.buttons.base.AbstractButton;
import br.com.yagofx.gadobot.player.AudioTrackWrapper;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.PagePair;
import br.com.yagofx.gadobot.util.PaginatedList;
import br.com.yagofx.gadobot.util.ParsingUtils;
import br.com.yagofx.gadobot.util.SimpleEmbeds;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.beans.factory.annotation.Qualifier;

public class FirstPage extends AbstractButton {

    private final GuildService guildService;

    public FirstPage(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public void run(ButtonInteractionEvent event) {
        event.deferEdit().queue();

        PagePair pages = new PagePair(ParsingUtils.extractPagesFrom(event.getMessage().getEmbeds().get(0).getFooter().getText()));

        PaginatedList<AudioTrackWrapper> queue = guildService.getTrackScheduler(event.getGuild()).getQueue();
        MessageEmbed embed = SimpleEmbeds.songList(queue.getFirstPage(), 1, pages.getLast()).build();

        event.getMessage().editMessageEmbeds().setEmbeds(embed).queue();
    }
}