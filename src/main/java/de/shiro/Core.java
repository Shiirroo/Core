package de.shiro;

import de.shiro.bukkitevents.OnPlayerJoin;
import de.shiro.bukkitevents.OnPlayerLeave;
import de.shiro.commands.commandbuilder.Command;
import de.shiro.commands.commandbuilder.CommandAPI;
import de.shiro.commands.commandbuilder.Commands;
import de.shiro.commands.OnCommand;
import de.shiro.commands.TabComplete;
import de.shiro.system.action.PlayerActionHotbar;
import de.shiro.system.action.manager.event.ActionEventManager;
import de.shiro.system.config.ISession;
import de.shiro.system.permission.PermManager;
import de.shiro.utlits.Config;
import de.shiro.utlits.ConfigManager;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class Core extends JavaPlugin implements EventListener, @NotNull Listener, CommandExecutor, TabCompleter {

    @Getter
    private static Plugin Instance;

    @Getter
    private static final List<ISession> iSessions = new ArrayList<>();

    @Getter
    private static final ActionEventManager actionEventManager = new ActionEventManager();
    @Getter
    private static final String Prefix = ChatColor.DARK_GRAY +"["+ ChatColor.RESET+ ChatColor.GOLD + "CORE" +ChatColor.DARK_GRAY +"] "+ ChatColor.GRAY ;
    @Getter
    private static PermManager permManager = null;

    //@Getter
    //private final DataBaseManager dataBaseManager = new DataBaseManager();


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Instance = this;
        permManager = new PermManager(this, new ConfigManager(this));
        registerCommands();
        registerISessions();


        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLeave(), this);


        new PlayerActionHotbar().start();

    }

    public void registerCommands(){
        Commands[] commands = Commands.values();


        for(Commands command : commands){
            CommandAPI commandAPI = new CommandAPI(command);
            PluginCommand cmd = getCommand(command.getName());
            if(cmd != null){
                cmd.setExecutor(new OnCommand(commandAPI));
                cmd.setTabCompleter(new TabComplete(commandAPI));
                //cmd.setPermission(commandAPI.getPermission());
                cmd.setName(command.getName());
                cmd.setDescription("");
            }
        }
    }

    public void registerISessions(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            ISession.getOrAddISession(player.getUniqueId(), player.getName());
        });
    }








    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
