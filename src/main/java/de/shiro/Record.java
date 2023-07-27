package de.shiro;

import de.shiro.api.blocks.Point3;
import de.shiro.manager.Manager;
import de.shiro.utlits.Lag;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class Record extends JavaPlugin  {
    @Getter
    private static Plugin Instance;
    @Getter
    private static Manager manager;
    @Getter
    private static final String Prefix = ChatColor.DARK_GRAY +"["+ ChatColor.RESET+ ChatColor.GOLD + "Record" +ChatColor.DARK_GRAY +"] "+ ChatColor.GRAY ;

    @Override
    public void onEnable() {
        Instance = this;
        manager = new Manager(this);
        manager.init();
    }


    @Override
    public void onDisable() {
        manager.close();
    }


}
