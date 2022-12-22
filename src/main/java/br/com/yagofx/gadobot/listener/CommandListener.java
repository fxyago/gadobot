package br.com.yagofx.gadobot.listener;

import br.com.yagofx.gadobot.commands.base.Command;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.ParsingUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@Component("CommandListener")
public class CommandListener extends ListenerAdapter {

    @Getter
    private static final HashMap<String, Command> COMMAND_MAP = new HashMap<>();

    private final GuildService guildService;

    public CommandListener(@Qualifier("GuildServiceImpl") GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (isPrivateMessate(event)) return;
        executeCommandOf(event);
    }

    private void executeCommandOf(@NotNull MessageReceivedEvent event) {
        String prefix = guildService.getPrefix(event.getGuild().getId()).toString();

        if (event.getMessage().getContentRaw().startsWith(prefix)) {
            var command = COMMAND_MAP.get(ParsingUtils.extractCommandFrom(event.getMessage().getContentRaw()));
            if (command == null) {
                event.getChannel().sendMessage("N tem esse comando ai n feio").queue();
                return;
            }
            command.run(event);
        }
    }

    private boolean isPrivateMessate(@NotNull MessageReceivedEvent event) {
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

    public static void addCommand(Command abstractCommand) {
        log.info("Adicionando comando: " + abstractCommand.name());
        abstractCommand.getAliases().forEach(s -> COMMAND_MAP.put(s, abstractCommand));
    }

}
