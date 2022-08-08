package de.shiro.system.action;

import de.shiro.Core;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.ActionCookies;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Log;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.units.qual.A;

import java.util.Comparator;
import java.util.Optional;
import java.util.TimerTask;

public class PlayerActionHotbar extends Thread {

    @Override
    public void run() {
        Log.info(Thread.currentThread().getName() + ": " + "PlayerActionHotbar started");
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (ISession iSession : Core.getISessions()) {

                if (iSession.getPlayerAction().hasActionFuture()) {
                    ActionFuture<?, ?> actionFuture = iSession.getPlayerAction().getActionFuture();
                    AbstractAction<?, ?> abstractAction = actionFuture.getAction();
                    Integer duration = abstractAction.getCookies().getDurationSeconds();
                        Player player = iSession.getSessionPlayer();
                        if (player != null && checkShowHotbar(abstractAction.getCookies())){
                            ActionFuture<?, ?> subAction = abstractAction.getCookies().getSubActions().stream().filter(f -> !f.getAction().getCookies().isFinished() && f.getAction().getCookies().isSubAction()).max(Comparator.comparing(f -> f.getAction().getCookies().getDuration())).orElse(null);
                            if(subAction != null){
                                sendActionBar(subAction.getAction().getClass().getSimpleName(), duration, player, iSession.getPlayerAction().getActionFutureQueue().size());
                            } else {
                                sendActionBar(abstractAction.getClass().getSimpleName(), duration, player, iSession.getPlayerAction().getActionFutureQueue().size());
                            }
                    }
                }
            }
        }
    }

    public void sendActionBar(String actionName, Integer Duration, Player player, Integer queueSize){
        if (queueSize > 0){
            player.sendActionBar(ChatColor.GREEN + actionName+ " : " + Duration + "s" + " (" + queueSize + ")");
        } else {
            player.sendActionBar(ChatColor.GREEN + actionName + " : " + Duration + "s");
        }
    }

    public boolean checkShowHotbar(ActionCookies actionCookies) {
        if(actionCookies.isFinished()) return false;
        return actionCookies.getDurationSeconds() >= 1;
    }

}
