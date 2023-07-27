package de.shiro.system.action.manager.event;

import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.utlits.log.Log;
import de.shiro.utlits.log.TraceHelper;
import org.bukkit.ChatColor;

public class ActionFinishedListener implements ActionFinishedEvent {

    @Override
    public <R, F extends ActionFuture<R, ?>> void onActionFinished(F actionFuture) {
        var action = actionFuture.getAction();
        actionFuture.getAction().getCookies().setFinished();
        switch (actionFuture.getState()) {
            case Success -> Success(actionFuture,   action);
            case Failed -> Failed(actionFuture, action);
            case Cancelled -> Cancelled(actionFuture, action);
            case Timeout -> Timeout(actionFuture, action);
        }
        startNextAction(action);
    }

    private <R, F extends ActionFuture<R, ?>> void Failed(F actionFuture, AbstractAction<R,?> action) {
        if(actionFuture.getState().getMessage() instanceof Exception exception){
            TraceHelper.sendPlayerStackTrace(action.getConfig().getISession().getSessionPlayer(), exception.getStackTrace());
            Log.error((action.getCookies().isSubAction()? "Sub": "") + "Action " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " failed!" + exception.getMessage());
        };
    }

    private <R, F extends ActionFuture<R, ?>> void Success(F actionFuture, AbstractAction<R,?> action) {
        if(actionFuture.getState().getMessage() != null) Log.info("Action " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " " + actionFuture.getState().getMessage());
        Log.info((action.getCookies().isSubAction()? "Sub": "")+ "Action " +"was finished: " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " from " + action.getConfig().getISession().getExecutorName() + ChatColor.YELLOW + " in " + action.getCookies().getDuration() + "ms");

    }

    private <R, F extends ActionFuture<R, ?>> void Cancelled(F actionFuture, AbstractAction<R,?> action) {
        Log.info((action.getCookies().isSubAction()? "Sub": "") + "Action " +"was cancelled: " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " from " + action.getConfig().getISession().getExecutorName());
    }

    private <R, F extends ActionFuture<R, ?>> void Timeout(F actionFuture, AbstractAction<R,?> action) {
        if(actionFuture.getState().getMessage() != null) Log.info("Action " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " " + actionFuture.getState().getMessage());
        Log.info((action.getCookies().isSubAction()? "Sub": "") +"Action " +"has been timed out: " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " from " + action.getConfig().getISession().getExecutorName() + ChatColor.YELLOW +  " in " + action.getCookies().getDuration() + "ms");
    }




    public <R> void startNextAction(AbstractAction<R,?>  action) {
        if(action.getCookies().isSkipQue()) return;
        ActionFuture<?,?> nextActionFuture = action.getConfig().getISession().getPlayerAction().getNextActionFuture();
        if(nextActionFuture != null){
            nextActionFuture.execute();
        }
    }

}
