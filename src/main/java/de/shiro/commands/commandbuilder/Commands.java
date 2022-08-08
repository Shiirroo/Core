package de.shiro.commands.commandbuilder;

import de.shiro.commands.action.ActionCommands;
import de.shiro.commands.action.ActionFacade;
import de.shiro.commands.chunk.ChunkCommands;
import de.shiro.commands.commandbuilder.CommandsInternal;
import de.shiro.commands.pos.PosCommands;
import lombok.Getter;

public enum Commands {
    POS(new PosCommands()),
    ACTION(new ActionCommands(new ActionFacade())),
    CHUNK(new ChunkCommands());
    @Getter
    private final CommandsInternal commandsInternal;

    Commands(CommandsInternal commandsInternal) {
        this.commandsInternal = commandsInternal;
    }

    public String getName() {
        return name().charAt(0) + name().substring(1).toUpperCase();
    }

}
