package de.shiro.commands.commandbuilder;

import de.shiro.system.config.ISession;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandThread extends Thread {

    @Getter
    private final Method method;
    @Getter
    private final CommandsInternal commandsInternal;
    @Getter
    private final ISession iSession;
    @Getter
    private final CommandSender sender;
    @Getter
    private final CommandArguments commandArguments;
    @Getter
    private Object result;

    public CommandThread(Method method, CommandsInternal commandsInternal, ISession iSession, CommandSender sender, CommandArguments commandArguments) {
        this.method = method;
        this.commandsInternal = commandsInternal;
        this.iSession = iSession;
        this.sender = sender;
        this.commandArguments = commandArguments;
    }


    @Override
    public void run() {
        try {
            result = method.invoke(commandsInternal, iSession, sender, commandArguments);
        } catch (IllegalAccessException | InvocationTargetException ignored) {

        }
    }


}
