package de.shiro.utlits;

import jdk.jfr.StackTrace;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.PrintStream;
import java.util.Arrays;

public class TraceHelper {

    public static void sendPlayerTraceInfo(Player player, String message) {
        if(player != null) {
            player.sendMessage(message);
        } else {
            Log.error(message);
        }
    }

    public static void sendPlayerStackTrace(Player player, StackTraceElement... stackTraceElements) {
        if (player != null) {
            if (stackTraceElements != null && stackTraceElements.length > 0) {
                StackTraceElement stackTrace = stackTraceElements[0];
                String stackTraceString = "Trace:" + stackTrace.getMethodName() + "(" + stackTrace.getFileName() + ":" + stackTrace.getLineNumber() + ")";
                TextComponent textComponent = Component
                        .text(ChatColor.GREEN + stackTraceString)
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, stackTraceString));
                player.sendMessage(textComponent);
            }
        } else {
            Log.error(Arrays.toString(stackTraceElements));
        }
    }




    }
