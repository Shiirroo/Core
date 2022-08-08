package de.shiro.commands.action;

import de.shiro.commands.commandbuilder.Command;
import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.commands.commandbuilder.CommandsInternal;
import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.system.config.ISession;
import org.bukkit.command.CommandSender;

public interface ActionCommandsInternal extends CommandsInternal {

    @Command(aliases = "list",
            syntax = {CKey.PlayerActions, CKey.Page})
    void list(ISession iSession, CommandSender sender, CommandArguments args);

    @Command(aliases = "clear")
    void clear(ISession iSession, CommandSender sender, CommandArguments args);


}
