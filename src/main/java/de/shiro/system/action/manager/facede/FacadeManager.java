package de.shiro.system.action.manager.facede;

import de.shiro.Record;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.ActionType;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.utlits.log.Log;
import de.shiro.utlits.log.TraceHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class FacadeManager {

    private final Class<?> internal;

    public FacadeManager(Class<?> clazz) {
        if(clazz.getInterfaces().length != 1) {
            throw new IllegalArgumentException("Class must implement at least one interface");
        }
        this.internal = clazz.getInterfaces()[0];
    }



    public <R,U extends AbstractAction<R,? extends AbstractActionConfig>> ActionFuture<R, U> addAction(AbstractActionConfig config, Enum<?> actionName) {
        if (config.getActionType() == ActionType.ASYNC) {
           return addAsyncAction(config, actionName);
        }
        return addSyncAction(config, actionName);
    }

    public <R,U extends AbstractAction<R,? extends AbstractActionConfig>> ActionFuture<R, U> addAsyncAction(AbstractActionConfig config, Enum<?> actionName)
    {
        ActionFuture<R, U> future = getActionFuture(config,actionName);
        if(future == null) return null;
        if (Bukkit.isPrimaryThread()) Bukkit.getScheduler().runTaskAsynchronously(Record.getInstance(), future);
        else future.execute();
        return future;
    }

    public <R,U extends AbstractAction<R,? extends AbstractActionConfig>> ActionFuture<R, U> addSyncAction(AbstractActionConfig config,Enum<?> actionName)  {
        ActionFuture<R, U> future = getActionFuture(config,actionName);
        if(future == null) return null;
        if(Bukkit.isPrimaryThread()) future.execute();
        else Bukkit.getScheduler().runTask(Record.getInstance(), future::execute);
        return future;
    }

    private  <R,U extends AbstractAction<R,? extends AbstractActionConfig>> ActionFuture<R, U> getActionFuture(AbstractActionConfig config, Enum<?> actionName) {
        U ob = null;
        try {
            ob = (U) findObject(config,actionName);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            Log.error("Could not create Action " + actionName.toString());

            TraceHelper.sendPlayerStackTrace(config.getISession().getSessionPlayer(), e.getStackTrace());
            for(StackTraceElement element : e.getStackTrace()){
                Log.error(element);
            }



            //config.getISession().sendSessionMessage(ChatColor.RED + "Could not create Action " + ChatColor.DARK_RED + actionName);
        }
        if(ob == null) return null;
        return new ActionFuture<>(ob);
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
