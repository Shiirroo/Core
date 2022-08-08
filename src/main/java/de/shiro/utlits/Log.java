package de.shiro.utlits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.nio.Buffer;
import java.util.Arrays;

import static org.bukkit.Bukkit.getServer;

public class Log {


    public static void send(String message) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.GREEN + "CORE")  + ChatColor.GREEN + message);
    }

    public static void send(String... message) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.GREEN + "CORE")  + Arrays.toString(message));
    }

    public static void send(Object object) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.GREEN + "CORE")  + ChatColor.GREEN + object.toString());
    }

    public static void send(Object... objects) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.GREEN + "CORE") + ChatColor.GREEN + Arrays.toString(objects));
    }

    public static void error(String message) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.DARK_RED + "ERROR") + ChatColor.DARK_RED + message);
    }
    public static void error(String... message) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.DARK_RED + "ERROR") + ChatColor.DARK_RED +  Arrays.toString(message));
    }

    public static void error(Object object) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.DARK_RED + "ERROR") + ChatColor.DARK_RED + object.toString());
    }

    public static void error(Object... objects) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.DARK_RED + "ERROR") + ChatColor.DARK_RED + Arrays.toString(objects));
    }

    public static void info(String message) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.YELLOW + "INFO") + ChatColor.YELLOW + message);
    }

    public static void info(String... message) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.YELLOW + "INFO") + ChatColor.YELLOW + Arrays.toString(message));
    }

    public static void info(Object object) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.YELLOW + "INFO") + ChatColor.YELLOW + object.toString());
    }
    public static void info(Object... objects) {
        getServer().getConsoleSender().sendMessage(getPrefix(ChatColor.YELLOW + "INFO") + ChatColor.YELLOW + Arrays.toString(objects));
    }


    private static String getPrefix(String prefix) {
        return ChatColor.GRAY +"[" + prefix + ChatColor.GRAY + "] ";
    }


}
