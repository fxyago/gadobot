package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Value;

public class About extends AbstractCommand {

    private final String name;
    private final String version;

    public About(
            @Value("${application.name}") String name,
            @Value("${build.version}") String version) {
        this.name = name;
        this.version = version;
    }

    @Override
    public void run(Event event) {
        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
        MessageEmbed embed = new EmbedBuilder()
                .setTitle(String.format("%s v%s", name, version))
                .setDescription("\u00AD\nLinks uteis:\n\n[GitHub](https://github.com/fxyago/gadobot)\n[Twitter](https://www.twitter.com/fxyago)\n\u00AD")
                .setFooter("Feito por yago#5476")
                .build();
        messageEvent.getChannel().sendMessageEmbeds(embed).queue();
    }

    @Override
    public String helpDescription() {
        return ":point_right: Mostra informações sobre o bot e o autor";
    }
}
