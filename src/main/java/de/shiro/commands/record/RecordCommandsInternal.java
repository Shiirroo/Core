package de.shiro.commands.record;

import de.shiro.commands.commandbuilder.Command;
import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.commands.commandbuilder.CommandsInternal;
import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.system.config.ISession;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.units.qual.C;

public interface RecordCommandsInternal extends CommandsInternal {

    @Command(aliases = "lookup",
            syntax = {CKey.TimeTo, CKey.TimeFrom, CKey.RANGE, CKey.Amount})
    void lookup(ISession iSession, CommandSender sender, CommandArguments args);

    @Command(aliases = "type",
            syntax = {CKey.RECORDTYP, CKey.TimeTo, CKey.TimeFrom, CKey.Amount})
    void type(ISession iSession, CommandSender sender, CommandArguments args);


    @Command(aliases = "config",
            syntax = {CKey.LOGCONFIG})
    void config(ISession iSession, CommandSender sender, CommandArguments args);



}
