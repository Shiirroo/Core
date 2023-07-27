package de.shiro.utlits.log;

import de.shiro.utlits.log.Log;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TraceHelper {

    public static void sendPlayerTraceInfo(Player player, String message) {
        if(player != null) {
            player.sendMessage(message);
        } else {
            Log.error(message);
        }
    }

    public static void sendPlayerStackTrace(Player player, StackTraceElement[] stackTraceElements) {
        if (player != null) {
            if (stackTraceElements.length > 0) {
                StackTraceElement errorLocation = stackTraceElements[0];
                System.out.println("Error occurred in file: " + errorLocation.getFileName());
                TextComponent textComponent = getTextComponent(errorLocation);
                player.spigot().sendMessage(textComponent);
            } else {
                System.out.println("Unable to determine error location.");
            }
        } else {
            Log.error(Arrays.toString(stackTraceElements));
        }
    }


    public static void sendPlayerStackTrace(Player player) {
        if (player != null) {
            //StackWalker.StackFrame stackTrace = StackWalker.getInstance().walk(s -> s.skip(depth).limit(1).findFirst().get());
            StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
            if (stackTraceElements.length > 1) {
                System.out.println(Arrays.toString(stackTraceElements));
                System.out.println(stackTraceElements[1]);
                TextComponent textComponent = getTextComponent(stackTraceElements[1]);
                player.spigot().sendMessage(textComponent);
            } else {
                Log.error("Something wrong while sending StackTrace");
            }
        }
    }

    private static TextComponent getTextComponent(StackTraceElement stackTrace) {
        String stackTraceString = stackTrace.getFileName() + " " + stackTrace.getLineNumber();
        TextComponent textComponent = new TextComponent();
        textComponent.setText("TRACE: ( " + stackTraceString + " ) ");
        textComponent.setColor(ChatColor.GREEN);
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, stackTraceString);
        textComponent.setClickEvent(clickEvent);
        return textComponent;
    }


}
