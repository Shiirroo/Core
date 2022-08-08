package de.shiro.api.types;

import org.bukkit.ChatColor;
import org.bukkit.World;

import java.util.Arrays;

public enum WorldTypColor {
    NORMAL(ChatColor.GREEN, World.Environment.NORMAL),
    NETHER(ChatColor.DARK_RED,World.Environment.NETHER),
    END(ChatColor.DARK_PURPLE, World.Environment.THE_END),
    CUSTOM(ChatColor.GRAY, World.Environment.CUSTOM);


    final ChatColor color;
    final World.Environment environment;

    public static WorldTypColor getWorldTypColor(World.Environment environment) {
        return Arrays.stream(WorldTypColor.values()).filter(worldTypColor -> worldTypColor.environment == environment).findFirst().orElse(null);
    }

    public String getWorldTypIcon() {
        return ChatColor.GRAY + "["+ color + "Ⓦ"+ChatColor.GRAY+ "]";
    }

    WorldTypColor(ChatColor chatColor, World.Environment environment) {
        this.color = chatColor;
        this.environment = environment;
    }


    public ChatColor getColor() {
        return color;
    }


}
