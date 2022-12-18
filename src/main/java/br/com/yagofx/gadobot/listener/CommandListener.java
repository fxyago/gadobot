package br.com.yagofx.gadobot.listener;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.ParsingUtils;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@Component("CommandListener")
public class CommandListener extends ListenerAdapter {

    private final GuildService guildService;
    private final HashMap<String, Function<Event, Void>> commandMap;

    public CommandListener(@Qualifier("GuildServiceImpl") GuildService guildService) {
        this.commandMap = new HashMap<>();
        this.guildService = guildService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (isMensagemPrivada(event)) return;
        executarComando(event);
    }

    private void executarComando(@NotNull MessageReceivedEvent event) {
        String prefix = guildService.getPrefix(event.getGuild().getId()).toString();

        if (event.getMessage().getContentRaw().startsWith(prefix)) {
            commandMap.get(ParsingUtils.parseCommand(event.getMessage().getContentRaw())).apply(event);
        }
    }

    private boolean isMensagemPrivada(@NotNull MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE)) {
            respondPrivateMessage(event);
            return true;
        }
        return false;
    }

    private void respondPrivateMessage(@NotNull MessageReceivedEvent event) {
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage("Muuuuuuuu").queueAfter(1500, MILLISECONDS);
    }

    public void addCommand(AbstractCommand abstractCommand) {
        log.error("Adicionando comando: " + abstractCommand.getName());
        abstractCommand.getAliases().forEach(s -> {
            commandMap.put(s, abstractCommand::run);
        });
    }

}
