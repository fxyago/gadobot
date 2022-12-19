package br.com.yagofx.gadobot.commands.base;

import br.com.yagofx.gadobot.annotation.InheritedComponent;
import br.com.yagofx.gadobot.listener.CommandListener;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@InheritedComponent
public abstract class AbstractCommand implements Command {

    @Getter
    private final String name = this.getClass().getSimpleName();
    private CommandListener listener;

    @PostConstruct
    protected final void subscribe() {
        listener.addCommand(this);
    }

    @Autowired
    public void setListener(CommandListener listener) {
        this.listener = listener;
    }
}
