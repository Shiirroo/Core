package de.shiro.commands;

import de.shiro.commands.commandbuilder.CommandAPI;
import de.shiro.system.config.ISession;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {

    private final CommandAPI commandAPI;

    public TabComplete(CommandAPI commandAPI) {
        this.commandAPI = commandAPI;
    }


    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args){
        if(sender instanceof Player player) {
            if (args.length == 1) return commandAPI.getSubCommands();
            else if (args.length > 1)
                return commandAPI.getCommands(ISession.getOrAddISession(player.getUniqueId(), player.getName()), args);
            }
        return new ArrayList<>();
    };


}
