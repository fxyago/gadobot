package br.com.yagofx.gadobot.buttons.base;

import br.com.yagofx.gadobot.listener.ButtonListener;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface Button {

    default String name() {
        return this.getClass().getSimpleName();
    }

    void run(ButtonInteractionEvent event);

    @PostConstruct
    default void subscribe() {
        ButtonListener.addButton(this);
    }

}
