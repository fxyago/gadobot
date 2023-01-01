package br.com.yagofx.gadobot.commands.bot;

import br.com.yagofx.gadobot.commands.base.AbstractCommand;
import br.com.yagofx.gadobot.service.impl.GuildServiceImpl;
import br.com.yagofx.gadobot.util.ParsingUtils;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
public class Prefix extends AbstractCommand {

    private final GuildServiceImpl guildServiceImpl;

    public Prefix(
            @Qualifier("GuildServiceImpl")
            GuildServiceImpl guildServiceImpl) {
        this.guildServiceImpl = guildServiceImpl;
    }

    @Override
    public void run(MessageReceivedEvent messageEvent) {
        String message = String.format("Prefixo atual: `%s`", guildServiceImpl.getPrefix(messageEvent.getGuild().getId()));
        try {
            String args = ParsingUtils.extractArgsFrom(messageEvent.getMessage().getContentRaw());
            guildServiceImpl.setPrefix(messageEvent.getGuild().getId(), message.charAt(0));
            message = String.format("Prefixo modificado para: `%s`", args.charAt(0));
        } catch (IndexOutOfBoundsException ioobe) {
            log.debug("Exception na classe {}, {}, enviando mensagem padrao", this.name(), ioobe.getLocalizedMessage());
        }
        messageEvent.getChannel().sendMessage(message).queue();
    }

    @Override
    public String helpDescription() {
        return "*Muda o prefixo para os comandos no servidor.\n*Prefixo padrão: `;`";
    }

}
