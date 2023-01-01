package br.com.yagofx.gadobot.listener;

import br.com.yagofx.gadobot.buttons.base.Button;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class ButtonListener extends ListenerAdapter {

    private static final HashMap<String, Button> BUTTONS_MAP = new HashMap<>();

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String id = event.getButton().getId();
        Button button = BUTTONS_MAP.get(id);

        if (button != null) button.run(event);
        else log.error("Botao nao encontrado, id procurado: {}", id);
    }

    public static void addButton(Button button) {
        log.info("Adicionando botao: " + button.name());
        BUTTONS_MAP.put(button.name(), button);
    }

}
