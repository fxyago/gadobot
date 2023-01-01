package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.player.AudioTrackWrapper;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.CommonEmojis;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NowPlaying extends AbstractCommand {

    private final GuildService guildService;

    public NowPlaying(
            @Qualifier("GuildServiceImpl")
            GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public List<String> getAliases() {
        return List.of("nowplaying", "np");
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {
        AudioTrackWrapper nowPlaying = guildService.getTrackScheduler(messageEvent.getGuild()).getNowPlaying();

        if (nowPlaying == null) {
            messageEvent.getChannel().sendMessage("Tem nada tocando agora nao bro").queue();
        } else {
            messageEvent.getMessage().addReaction(CommonEmojis.THUMBS_UP).queue();
            messageEvent.getChannel().sendMessageEmbeds(montarEmbedDe(nowPlaying)).queueAfter(2, TimeUnit.SECONDS);
        }
    }

    @Override
    public String helpDescription() {
        return "*Mostra detalhes sobre a música que esta tocando no momento";
    }

    private MessageEmbed montarEmbedDe(AudioTrackWrapper trackWrapper) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setAuthor("Tocando agora:")
                .setTitle(trackWrapper.getSongName(), trackWrapper.getTrack().getInfo().uri)
                .setDescription("Pedido por: " + trackWrapper.getMember().getAsMention());
        return embedBuilder.build();
    }

}
