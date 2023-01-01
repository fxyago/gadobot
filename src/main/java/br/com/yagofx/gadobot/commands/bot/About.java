package br.com.yagofx.gadobot.commands.bot;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.util.SimpleEmbeds;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Value;

public class About extends AbstractCommand {

    private final String aboutTitle;
    private final String aboutBody;

    public About(
            @Value("${about.title}") String aboutTitle,
            @Value("${about.body}") String aboutBody) {
        this.aboutTitle = aboutTitle;
        this.aboutBody = aboutBody;
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {
        MessageEmbed aboutEmbed = SimpleEmbeds.about(aboutTitle, aboutBody).build();
        messageEvent.getChannel().sendMessageEmbeds(aboutEmbed).queue();
    }

    @Override
    public String helpDescription() {
        return ":point_right: Mostra informações sobre o bot e o autor";
    }
}
