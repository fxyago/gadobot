package br.com.yagofx.gadobot.buttons.queue;

import br.com.yagofx.gadobot.buttons.base.AbstractButton;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.PagePair;
import br.com.yagofx.gadobot.util.ParsingUtils;
import br.com.yagofx.gadobot.util.SimpleEmbeds;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.beans.factory.annotation.Qualifier;

public class LastPage extends AbstractButton {

    private final GuildService guildService;

    public LastPage(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public void run(ButtonInteractionEvent event) {
        event.deferEdit().queue();

        PagePair pages = new PagePair(ParsingUtils.extractPagesFrom(event.getMessage().getEmbeds().get(0).getFooter().getText()));

        final var queue = guildService.getTrackScheduler(event.getGuild()).getQueue();

        event.getMessage().editMessageEmbeds().setEmbeds(SimpleEmbeds.songList(queue.getLastPage(), pages.getLast(), pages.getLast()).build()).queue();
        event.deferEdit().queue();
    }
}
