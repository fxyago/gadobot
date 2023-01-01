package br.com.yagofx.gadobot.commands.player;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.player.AudioTrackWrapper;
import br.com.yagofx.gadobot.service.GuildService;
import br.com.yagofx.gadobot.util.ParsingUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Move extends AbstractCommand {

    private final GuildService guildService;

    public Move(GuildService guildService) {
        this.guildService = guildService;
    }

    @Override
    public List<String> getAliases() {
        return List.of("move", "mv");
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {
        try {
            String[] args = ParsingUtils.extractArgsFrom(messageEvent.getMessage().getContentRaw()).split(",");

            Integer fromPosition = Integer.parseInt(args[0].trim());
            Integer toPosition = Integer.parseInt(args[1].trim());

            AudioTrackWrapper movedTrack = guildService.getTrackScheduler(messageEvent.getGuild()).move(fromPosition, toPosition);

            messageEvent.getChannel().sendMessageEmbeds(new EmbedBuilder()
                    .setTitle("Música: " + movedTrack.getSongName())
                    .setDescription(String.format("Movido de posição %s para %s", fromPosition, toPosition))
                    .build()).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String helpDescription() {
        return "* Move musicas de lugar na lista\n* Ex. `;mv 25, 5` move a musica numero 25 da lista para a posicao 5";
    }
}
