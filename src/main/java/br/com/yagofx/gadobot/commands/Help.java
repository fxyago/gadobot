package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.commands.base.Command;
import br.com.yagofx.gadobot.listener.CommandListener;
import br.com.yagofx.gadobot.util.ParsingUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component("helpCommand")
public class Help extends AbstractCommand {

    @Override
    public void run(Event event) {
        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
        TextChannel channel = messageEvent.getChannel().asTextChannel();

        Command command;

        try {
            String args = ParsingUtils.extractArgsFrom(messageEvent.getMessage().getContentRaw());

            command = CommandListener.getCOMMAND_MAP().get(args);
            if (command == null) {
                channel.sendTyping().queue();
                channel.sendMessage("Nao achei esse comando ai, vc escreveu esse treco certo parsa?").queueAfter(1000, TimeUnit.SECONDS);
                return;
            }

            this.sendHelpDefinition(new EmbedBuilder()
                    .setAuthor("Comando: " + command.name())
                    .setTitle("Aliases: " + ParsingUtils.formatCommandAliases(command.getAliases())),
                command.helpDescription(), channel);

        } catch (IndexOutOfBoundsException ioobe) {
            List<String> commands = new HashSet<>(CommandListener.getCOMMAND_MAP().values()).stream().map(c -> c.name().toLowerCase()).toList();

            this.sendHelpDefinition(new EmbedBuilder()
                    .setAuthor("Comandos disponiveis: "),
                ParsingUtils.formatCommandAliases(commands), channel);
        }
    }

    private void sendHelpDefinition(EmbedBuilder commandEmbed, String description, TextChannel channel) {
        MessageEmbed embed = commandEmbed
                .setDescription(description)
                .build();

        channel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public String helpDescription() {
        return ":point_right: Mostra essa mensagem de ajuda :smiley:\n:point_right: Quando usado com outro comando mostra a mensagem de ajuda do mesmo";
    }

}
