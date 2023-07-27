package de.shiro.commands.commandbuilder;

import de.shiro.commands.action.ActionCommands;
import de.shiro.commands.record.RecordCommands;
import lombok.Getter;

public enum Commands {
    ACTION(new ActionCommands()),
    RC(new RecordCommands()),

    ;
    @Getter
    private final CommandsInternal commandsInternal;

    Commands(CommandsInternal commandsInternal) {
        this.commandsInternal = commandsInternal;
    }

    public String getName() {
        return name().charAt(0) + name().substring(1).toUpperCase();
    }

}
