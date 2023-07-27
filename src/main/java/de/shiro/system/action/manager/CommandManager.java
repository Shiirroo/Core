package de.shiro.system.action.manager;

import de.shiro.Record;
import de.shiro.commands.OnCommand;
import de.shiro.commands.TabComplete;
import de.shiro.commands.commandbuilder.CommandAPI;
import de.shiro.commands.commandbuilder.Commands;
import de.shiro.manager.manager.IManager;
import de.shiro.record.Records;
import org.bukkit.command.PluginCommand;


public class CommandManager implements IManager {

    private final Record plugin;


    public CommandManager(Record core) {
        this.plugin = core;
    }


    public void registerCommands(){
        Commands[] commands = Commands.values();
        for(Commands command : commands){
            CommandAPI commandAPI = new CommandAPI(command);
            PluginCommand cmd = plugin.getCommand(command.getName());
            if(cmd != null){
                cmd.setExecutor(new OnCommand(commandAPI));
                cmd.setTabCompleter(new TabComplete(commandAPI));
                //cmd.setPermission(commandAPI.getPermission());
                cmd.setName(command.getName());
                cmd.setDescription("");
            }
        }
    }


    @Override
    public IManager init() {
        registerCommands();
        return this;
    }

    @Override
    public IManager close() {
        return this;
    }
}
