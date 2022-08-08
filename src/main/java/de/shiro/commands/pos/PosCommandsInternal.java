package de.shiro.commands.pos;

import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.commands.commandbuilder.Command;
import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.commands.commandbuilder.CommandsInternal;
import de.shiro.system.config.ISession;
import org.bukkit.command.CommandSender;

public interface PosCommandsInternal extends CommandsInternal {

    @Command(aliases = "save",
            syntax = {CKey.PosManage, CKey.Visibility})
    void save(ISession iSession, CommandSender sender, CommandArguments args);

    @Command(aliases = "list",
            syntax = {CKey.PosList, CKey.Page})
    void list(ISession iSession, CommandSender sender, CommandArguments args);

    @Command(aliases = "follow",
            syntax = {CKey.PosManage})
    void follow(ISession iSession, CommandSender sender, CommandArguments args);

    @Command(aliases = "delete",
            syntax = {CKey.PosManage})
    void delete(ISession iSession, CommandSender sender, CommandArguments args);

}
