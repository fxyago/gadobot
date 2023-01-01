package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.buttons.queue.FirstPage;
import br.com.yagofx.gadobot.buttons.queue.LastPage;
import br.com.yagofx.gadobot.buttons.queue.NextPage;
import br.com.yagofx.gadobot.buttons.queue.PreviousPage;
import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.SimpleEmbeds;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class Queue extends AbstractCommand {

    private final GuildService guildService;

    public Queue(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public List<String> getAliases() {
        return List.of("queue", "q", "tracklist");
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {
        final var queue = guildService.getTrackScheduler(messageEvent.getGuild()).getQueue();
        if (queue.isEmpty()) {
            messageEvent.getChannel().sendMessageEmbeds(SimpleEmbeds.notification("A fila de músicas está vazia", null).build()).queue();
            return;
        }

        MessageEmbed embed = SimpleEmbeds.songList(queue.getFirstPage(), 1, ((int) Math.ceil(queue.size() / 10d))).build();

        messageEvent.getChannel().sendMessageEmbeds(embed).addActionRow(
                Button.danger(FirstPage.class.getSimpleName(), Emoji.fromFormatted("<:first:901482748957585478>")),
                Button.danger(PreviousPage.class.getSimpleName(), Emoji.fromFormatted("<:prev:901482652991909928>")),
                Button.danger(NextPage.class.getSimpleName(), Emoji.fromFormatted("<:next:901482602064670741>")),
                Button.danger(LastPage.class.getSimpleName(), Emoji.fromFormatted("<:last:901482705311637574>"))
        ).queue();
    }

    @Override
    public String helpDescription() {
        return "*Mostra a fila de músicas em uma lista";
    }

}
