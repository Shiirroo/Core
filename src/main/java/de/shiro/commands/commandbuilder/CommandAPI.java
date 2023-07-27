package de.shiro.commands.commandbuilder;


import de.shiro.Record;
import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.commands.commandbuilder.ckey.syntax.CKeySyntax;
import de.shiro.system.config.ISession;
import de.shiro.utlits.log.Log;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.*;

public class CommandAPI  {
    @Getter
    private final Commands command;

    @Getter
    private List<Command> subCommands;

    public CommandAPI (Commands command) {
        this.command = command;
        registerSubCommands();
    }

    public String getPermission() {
        return (Record.getInstance().getName() +".command." + command.getName()).toLowerCase();
    }


    private void registerSubCommands() {
        List<Command> commands = new ArrayList<>();
        if(command.getCommandsInternal().getClass().getInterfaces().length != 1) return;
        Class<?> anInterface = command.getCommandsInternal().getClass().getInterfaces()[0];
        for (Method method : anInterface.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command c = method.getAnnotation(Command.class);
                commands.add(c);
            }
        }
        subCommands = commands;
        Log.info("Registered for [" + command + "] " + commands.size() + " subcommands");

    }


    public List<String> getSubCommandsString() {
        List<String> commands = new ArrayList<>();
        for (Command command : subCommands) {
            commands.add(command.aliases());
        }
        return commands;
    }

    public List<String> getSubCommands(String keyword) {
        List<String> commands = new ArrayList<>();
        for (Command command : subCommands) {
            String name = command.aliases();
            if(name.toLowerCase().contains(keyword.toLowerCase())) {
                commands.add(name);
            }
        }
        return commands;
    }



    public Command getSubCommand(String keyword) {
        return subCommands.stream().filter(command -> command.aliases().equalsIgnoreCase(keyword)).findFirst().orElse(null);
    }

    public List<String> getCommands(ISession iSession, String[] args) {
        List<String> commands = new ArrayList<>();
        Optional<Command> optionalCommand = subCommands.stream().filter(c -> c.aliases().equalsIgnoreCase(args[0])).findFirst();
        if(optionalCommand.isPresent()) {
            Command command = optionalCommand.get();
            CKey[] syntax = command.syntax();
            if(syntax.length > 0 && syntax.length >= args.length -1){
                CKey cKey = syntax[args.length - 2];
                CKeySyntax cKeySyntax = cKey.getCKeySyntax();
                Boolean pageable = cKeySyntax.getPageable();
                if(pageable) commands.add("*");
                commands.addAll(cKeySyntax.getSyntax().perform(iSession, new CommandArguments(args, command)));


            }
        }

        return commands;
    }









    public boolean performCommand(ISession iSession, CommandSender sender, String[] args) {
        if(args.length == 0) return false;
        String commandName = args[0];
        Command c = getSubCommand(commandName);
        if(c == null) return false;
        CommandsInternal commandsInternal = command.getCommandsInternal();
        Optional<Method> optionalMethod = Arrays.stream(commandsInternal.getClass().getDeclaredMethods()).filter(method -> method.getName().equalsIgnoreCase(commandName)).findFirst();
        if(optionalMethod.isEmpty()) return false;
        Method method = optionalMethod.get();
        CommandThread thread = new CommandThread(method,commandsInternal, iSession, sender, new CommandArguments(args, c));
        thread.start();
        return true;
    }

}
