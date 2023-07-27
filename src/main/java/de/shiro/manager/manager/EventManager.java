package de.shiro.manager.manager;

import de.shiro.bukkitevents.*;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class EventManager implements IManager {

    private final Plugin plugin;


    public EventManager(Plugin plugin){
        this.plugin = plugin;
    }

    private void registerEvents(){
        plugin.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new OnPlayerLeave(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new OnEntityDamageByEntity(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new OnBlockBreak(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new OnBlockPlace(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new OnPlayerDropItem(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new OnInventoryClick(), plugin);
    }

    @Override
    public IManager init() {
        registerEvents();
        return this;
    }

    @Override
    public IManager close() {
        return this;
    }
}
