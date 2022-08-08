package de.shiro.api.types;

import org.bukkit.ChatColor;

import java.util.Arrays;

public enum Visibility {
    PUBLIC(ChatColor.GREEN),
    PRIVATE(ChatColor.RED),
    PROTECTED(ChatColor.GOLD);

    final ChatColor color;

    public String getFormatName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public static Visibility getVisibility(String visibility) {
        return Arrays.stream(Visibility.values()).filter(v -> v.name().equalsIgnoreCase(visibility)).findFirst().orElse(PUBLIC);
    }


    public String getVisibilityIcon() {
        return ChatColor.GRAY + "["+ color + "Ⓥ"+ChatColor.GRAY+ "]";
    }

    Visibility(ChatColor chatColor) {
        this.color = chatColor;
    }

    public ChatColor getColor() {
        return color;
    }
}
