package de.shiro.commands.chunk;

import de.shiro.commands.commandbuilder.Command;
import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.commands.commandbuilder.CommandsInternal;
import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.system.config.ISession;
import org.bukkit.command.CommandSender;

public interface ChunkCommandsInternal extends CommandsInternal {

    @Command(aliases = "select",
            syntax = {CKey.ChunkFrom, CKey.ChunkTo})
    void select(ISession iSession, CommandSender sender, CommandArguments args);

    @Command(aliases = "save",
            syntax = {CKey.ChunkFrom, CKey.ChunkTo})
    void save(ISession iSession, CommandSender sender, CommandArguments args);


}
