package de.shiro.commands;

import de.shiro.commands.commandbuilder.CommandAPI;
import de.shiro.system.config.ISession;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OnCommand implements CommandExecutor {

    private final CommandAPI commandAPI;

    public OnCommand(CommandAPI commandAPI) {
        this.commandAPI = commandAPI;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length < 1 || !(sender instanceof Player player)) return false;
        ISession iSession = ISession.getOrAddISession(player.getUniqueId(), player.getName());
        commandAPI.performCommand(iSession, sender, args);
        return false;
    }

}
