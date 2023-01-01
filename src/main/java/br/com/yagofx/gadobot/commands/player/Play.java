package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.handlers.DelegatePlayHandler;
import br.com.yagofx.gadobot.util.CommonEmojis;
import br.com.yagofx.gadobot.util.SimpleEmbeds;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Play extends AbstractCommand {

    private final DelegatePlayHandler handler;

    public Play(DelegatePlayHandler handler) {
        this.handler = handler;
    }

    @Override
    public List<String> getAliases() {
        return List.of("play", "p");
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {
        Member member = messageEvent.getMember();
        Guild guild = messageEvent.getGuild();

        if (!isInVoicechannel(guild, member)) {
            messageEvent.getChannel().sendMessageEmbeds(SimpleEmbeds.notification("Entra numa sala de voz primeiro bro", null).build()).queue();
            return;
        }

        messageEvent.getMessage().addReaction(CommonEmojis.THUMBS_UP).queue();
        handler.loadAndPlayFrom(messageEvent);
    }

    private boolean isInVoicechannel(Guild guild, Member member) {
        for (VoiceChannel voiceChannel : guild.getVoiceChannels())
            if (voiceChannel.getMembers().contains(member)) return true;

        return false;
    }

    @Override
    public String helpDescription() {
        return "*Adiciona músicas a fila\n*Aceita links ou parametros para busca\n*Links podem ser do YouTube ou Spotify\n*Tambem aceita links de albums ou playlists";
    }

}
