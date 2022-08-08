package de.shiro.commands.commandbuilder;

import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.commands.commandbuilder.ckey.CKeyClasses;
import de.shiro.commands.commandbuilder.ckey.CommandSeparator;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Log;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Optional;

public class CommandArguments {

    @Getter
    String[] args;
    @Getter
    Command command;

    public CommandArguments(String[] args, Command command) {
        this.args = args;
        this.command = command;
    }


    public <T, E extends CKey> T getIfExists(ISession iSession, E key) {
        if(key.getCKeyClasses().equals(CKeyClasses.ENUM)) return (T) null;
        Optional<CKey> optional = Arrays.stream(command.syntax()).filter(c -> c.equals(key)).findFirst();
        if (optional.isPresent()) {
            Object[] objects = findParameters(key);
            if(objects != null) return getConstructor(key, null,objects);
        }
        return (T) null;
    }

    public <T, E extends CKey> T getIfExists(ISession iSession, E key, boolean forceInput) {
        if(key.getCKeyClasses().equals(CKeyClasses.ENUM)) return (T) null;
        Optional<CKey> optional = Arrays.stream(command.syntax()).filter(c -> c.equals(key)).findFirst();
        if (optional.isPresent()) {
            Object[] objects = findParameters(key);
            if(objects != null) {
                if(!(forceInput && forceInput(iSession, key, objects))) {
                    return getConstructor(key, null, objects);
                }
            }
        }
        return (T) null;
    }


    public <T> T getIfExists(ISession iSession, CKey key, T defaultValue) {
        Optional<CKey> optional = Arrays.stream(command.syntax()).filter(c -> c.equals(key)).findFirst();
        if (optional.isPresent()) {
            if(key.getCKeyClasses().equals(CKeyClasses.ENUM)) return getConstructorEnum(key, defaultValue);
            Object[] objects = findParameters(key);
            if(objects != null) return getConstructor(key, defaultValue,objects);
        }
        return defaultValue;
    }

    public <T> T getIfExists(ISession iSession, CKey key, T defaultValue, boolean forceInput) {
        Optional<CKey> optional = Arrays.stream(command.syntax()).filter(c -> c.equals(key)).findFirst();
        if (optional.isPresent()) {
            if(key.getCKeyClasses().equals(CKeyClasses.ENUM)) return getConstructorEnum(key, defaultValue);
            Object[] objects = findParameters(key);
            if(objects != null) {
                if(!(forceInput && forceInput(iSession, key, objects))) {
                    return getConstructor(key, defaultValue, objects);
                }
            }
        }
        return defaultValue;
    }

    public <T> T getIfExists(ISession iSession, CKey key, Enum<?>... defaultValue) {
        Optional<CKey> optional = Arrays.stream(command.syntax()).filter(c -> c.equals(key)).findFirst();
        if (optional.isPresent()) {
            if(key.getCKeyClasses().equals(CKeyClasses.ENUM))
                return getConstructorEnumValue(key, defaultValue);

        }
        return (T) null;
    }


    public boolean forceInput(ISession iSession, CKey key, Object... objects) {
        for (Object object : objects) {
            if (object == null) return true;
            if (key.getCKeySyntax().getSyntax().perform(iSession, this).stream().noneMatch(o -> o.equals(object))) {
                return true;
            }
        }
        return false;
    }


    public CommandArguments removeLast() {
        if(args.length > 0) {
            args = Arrays.copyOf(args, args.length - 1);
        }
       return this;
    }

    public String getTypingCommand() {
        if(args.length == 0) return "";
        return args[args.length - 1];
    }


    private Object[] findParameters(CKey key) {
            String[] rawParameters = new String[1];
            int index = Arrays.asList(command.syntax()).indexOf(key) + 1;
            if(args.length <= index) return null;
            if(key.getCKeyClasses().getCommandSeparator().equals(CommandSeparator.NO_SEPARATOR)){
                if(!key.getCKeySyntax().getPageable() && args[index].equalsIgnoreCase("*")) return null;
             rawParameters[0] = args[index];
            }
            else rawParameters = args[index].split(key.getCKeyClasses().getCommandSeparator().getSeparator());

            if(key.getCKeyClasses().getParameterTypes().length < rawParameters.length) return null;

            Object[] endParameters = new Object[rawParameters.length];

            for (int i = 0; i < rawParameters.length; i++) {
                endParameters[i] = getObject(rawParameters[i], key.getCKeyClasses().getParameterTypes()[i]);
            }
            return endParameters;
    }


    private <T> Object getObject(String parameter, Class<?> clazz) {
        if(clazz == int.class && isNumeric(parameter)) return Integer.parseInt(parameter);
        return parameter;
    }


    private <T> T getConstructorEnum(CKey key, T defaultValue) {
        if(defaultValue instanceof Enum<?> enumValue) {
            int index = Arrays.asList(command.syntax()).indexOf(key) + 1;
            if(args.length <= index) return defaultValue;
            if(key.getCKeyClasses().getCommandSeparator().equals(CommandSeparator.NO_SEPARATOR)) {
                Optional<?> enumOptional = Arrays.stream(enumValue.getDeclaringClass().getEnumConstants()).filter(e -> e.name().equalsIgnoreCase(args[index])).findFirst();
                if(enumOptional.isPresent()) return (T) enumOptional.get();
            }
        }
        return defaultValue;
    }

    public CKey getPageKeyIndicator(CKey key) {
        Optional<CKey> optional = Arrays.stream(command.syntax()).filter(c -> c.equals(key)).findFirst();
        if(optional.isPresent()){
            int index = Arrays.asList(command.syntax()).indexOf(key) - 1;
            if(index < 0) return null;
            return command.syntax()[index];
        }
        return null;
    }

    private <T> T getConstructorEnumValue(CKey key, Enum<?>... defaultValue) {
        int index = Arrays.asList(command.syntax()).indexOf(key) + 1;
        if(args.length <= index) return null;
        if(key.getCKeyClasses().getCommandSeparator().equals(CommandSeparator.NO_SEPARATOR)) {
            Optional<Enum<?>> optional = Arrays.stream(defaultValue).filter(e ->  e.name().equalsIgnoreCase(args[index])).findFirst();
            return (T) optional.orElse(null);
        }
        return null;
    }

    private <T> T getConstructor(CKey key, T defaultValue, Object... parameters) {
        try {
            Constructor<?> constructor = key.getCKeyClasses().getObjectClass().getConstructor(key.getCKeyClasses().getParameterTypes());
            return (T) constructor.newInstance(parameters);
        } catch (Exception e) {
            if (defaultValue != null) Log.error("Could not create instance of " + key.getCKeyClasses().getObjectClass().getSimpleName() + " with parameters " + Arrays.toString(parameters));
            return defaultValue;
        }
    }

    @Override
    public String toString() {
        return "CommandArguments{" +
                "args=" + Arrays.toString(args) +
                ", command=" + command +
                '}';
    }

    public static boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

}
