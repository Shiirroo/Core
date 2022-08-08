package de.shiro.system.action.manager.facede;

import de.shiro.Core;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.ActionType;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.utlits.Config;
import de.shiro.utlits.Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class FacadeManager {

    private final Class<?> internal;

    public FacadeManager(Class<?> clazz) {
        if(clazz.getInterfaces().length != 1) {
            throw new IllegalArgumentException("Class must implement at least one interface");
        }
        this.internal = clazz.getInterfaces()[0];
    }



    public <R,U extends AbstractAction<R,?>> ActionFuture<R, U> addAction(AbstractActionConfig config, Enum<?> actionName) {
        if (config.getActionType() == ActionType.ASYNC) {
            if (Bukkit.isPrimaryThread())
                Log.error("Thread is ín Main Thread. RunnableAction must be executed in a separate thread. Threat will be created automatically.");
            return addAsyncAction(config, actionName);
        }
        return addSyncAction(config, actionName);
    }

    public <R,U extends AbstractAction<R,?>> ActionFuture<R, U> addAsyncAction(AbstractActionConfig config, Enum<?> actionName) {
        if (Bukkit.isPrimaryThread()){
            Supplier<ActionFuture<R,U>> supplier = () -> getActionFuture(config, actionName);
            CompletableFuture<ActionFuture<R,U>> future = CompletableFuture.supplyAsync(supplier, Config.getService());
            if(future.isDone()) return future.join();
            return null;
        } else
            return getActionFuture(config,actionName);
    }

    public <R,U extends AbstractAction<R,?>> ActionFuture<R, U> addSyncAction(AbstractActionConfig config,Enum<?> actionName)  {
        return getActionFuture(config,actionName);
    }

    private  <R,U extends AbstractAction<R,?>> ActionFuture<R, U> getActionFuture(AbstractActionConfig config, Enum<?> actionName) {
        U ob = null;
        try {
            ob = (U) findObject(config,actionName);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            Log.error("Could not create Action " + actionName.toString());
            Log.error(e.getMessage());
            config.getISession().sendSessionMessage(ChatColor.RED + "Could not create Action " + ChatColor.DARK_RED + actionName);
        }
        if(ob == null) return null;
        ActionFuture<R, U> actionFuture = new ActionFuture<>(ob);
        actionFuture.execute();
        return actionFuture;
    }


    private <R,U extends AbstractAction<R,?>> AbstractAction<?,? extends AbstractActionConfig> findObject(AbstractActionConfig config, Enum<?> actionName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Optional<Method> method = Arrays.stream(internal.getDeclaredMethods()).filter(m -> m.isAnnotationPresent(FacadeAction.class) && m.getName().equalsIgnoreCase(actionName.toString())).findFirst();
        if (method.isPresent()) {
            Class<? extends AbstractAction<?,? extends AbstractActionConfig>> clazz = method.get().getDeclaredAnnotation(FacadeAction.class).action();
            return clazz.getConstructor(config.getClass()).newInstance(config);
        } else {
            Log.error(ChatColor.DARK_RED + "Action " + actionName.toString() + " not found");
            return null;
        }
    }
}
