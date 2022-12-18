package br.com.yagofx.gadobot.commands;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Summon extends AbstractCommand {

    @Override
    public List<String> getAliases() {
        return List.of("summon", "join");
    }

    @Override
    public Void run(Event event) {
        var messageEvent = (MessageReceivedEvent) event;

        var audioManager = messageEvent.getGuild().getAudioManager();
        if (audioManager.isConnected()) return null;

        if (!connectOnVoiceChannel(messageEvent))
            messageEvent.getChannel().sendMessage("Entra numa sala primeiro krl").queue();

        return null;
    }

    private boolean connectOnVoiceChannel(MessageReceivedEvent messageEvent) {
        var audioManager = messageEvent.getGuild().getAudioManager();

        for (VoiceChannel vc : audioManager.getGuild().getVoiceChannels()) {
            if (vc.getMembers().contains(messageEvent.getMember())) {
                audioManager.openAudioConnection(vc);
                return true;
            }
        }
        return false;
    }

}
