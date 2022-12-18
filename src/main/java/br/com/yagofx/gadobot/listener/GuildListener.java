package br.com.yagofx.gadobot.listener;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Component("GuildListener")
public class GuildListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        TextChannel channel = event.getGuild().getDefaultChannel()
                .asTextChannel();

        channel.sendTyping().queue();
        channel.sendMessage("CHEGUEI NESSE CARALHO PRA TOCAR UMAS PRA VCS").queueAfter(2, SECONDS);

        channel.sendTyping().queueAfter(3, SECONDS);
        channel.sendMessage("DIGO, MUUUUUUUUUUUUUUUUUU").queueAfter(5, SECONDS);
    }

}
