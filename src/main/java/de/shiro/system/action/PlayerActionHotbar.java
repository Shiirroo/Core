package de.shiro.system.action;

import de.shiro.Record;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.TimerTask;

public class PlayerActionHotbar extends TimerTask {

    @Override
    public void run() {
        //Log.info(Thread.currentThread().getName() + ": " + "PlayerActionHotbar started");
            for (ISession iSession : Record.getManager().getISessionManager().getISessions()) {
                if(iSession.isShowAction()) {
                    if (iSession.getPlayerAction().hasActionFuture()) {
                        ActionFuture<?, ?> actionFuture = iSession.getPlayerAction().getActionFuture();
                        AbstractAction<?, ?> abstractAction = actionFuture.getAction();
                        Integer duration = abstractAction.getCookies().getDurationSeconds();
                        Player player = iSession.getSessionPlayer();
                        if (player != null && checkShowHotbar(abstractAction.getCookies())) {
                            ActionFuture<?, ?> subAction = abstractAction.getCookies().getSubActions().stream().filter(f -> !f.getAction().getCookies().isFinished() && f.getAction().getCookies().isSubAction()).max(Comparator.comparing(f -> f.getAction().getCookies().getDuration())).orElse(null);
                            if (subAction != null) {
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
        TextComponent textComponent;
        if (queueSize > 0){
            textComponent = new TextComponent(actionName + " : " + Duration + "s" + " (" + queueSize + ")");
        } else {
            textComponent = new TextComponent(actionName + " : " + Duration + "s");
        }
        textComponent.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, Config.getServerUUID(),textComponent);
    }

    public boolean checkShowHotbar(ActionCookies actionCookies) {
        if(actionCookies.isFinished()) return false;
        return actionCookies.getDurationSeconds() >= 1;
    }

}
