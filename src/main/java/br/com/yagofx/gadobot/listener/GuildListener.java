package br.com.yagofx.gadobot.listener;

import br.com.yagofx.gadobot.commands.player.Leave;
import br.com.yagofx.gadobot.service.GuildService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Component("GuildListener")
public class GuildListener extends ListenerAdapter {

    private final GuildService guildService;

    public GuildListener(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        TextChannel channel = event.getGuild().getDefaultChannel()
                .asTextChannel();

        channel.sendTyping().queue();
        channel.sendMessage("CHEGUEI NESSE CARALHO PRA TOCAR UMAS PRA VCS").queueAfter(2, SECONDS);

        channel.sendTyping().queueAfter(3, SECONDS);
        channel.sendMessage("DIGO, MUUUUUUUUUUUUUUUUUU").queueAfter(5, SECONDS);
    }

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        boolean isMemberSelf = event.getMember().getId().equals(event.getJDA().getSelfUser().getId());
        if (isMemberSelf) return;

        var myChannel = event.getGuild().getAudioManager().getConnectedChannel();
        if (myChannel == null) return;

        int size = myChannel.asVoiceChannel().getMembers().size();
        guildService.getTrackScheduler(event.getGuild()).timerOverride(size == 1 ? Leave.Reason.LEFT_ALONE : null);
    }
}
