package de.shiro.system.action.manager.event;

import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.utlits.Config;
import de.shiro.utlits.Log;
import de.shiro.utlits.TraceHelper;
import org.checkerframework.checker.units.qual.A;

public class ActionFinishedListener implements ActionFinishedEvent {

    @Override
    public <R, F extends ActionFuture<R, ?>> void onActionFinished(F actionFuture) {
        actionFuture.getAction().getCookies().setFinished();
        switch (actionFuture.getState()) {
            case Success -> Success(actionFuture);
            case Failed -> Failed(actionFuture);
            case Cancelled -> Cancelled(actionFuture);
            case Timeout -> Timeout(actionFuture);
            default -> {
            }
        }
        startNextAction(actionFuture.getAction());
    }

    private <R, F extends ActionFuture<R, ?>> void Failed(F actionFuture) {
        if(actionFuture.getState().getMessage() instanceof Exception exception){
            AbstractAction<R,?> action = actionFuture.getAction();
            TraceHelper.sendPlayerStackTrace(action.getConfig().getISession().getSessionPlayer(), exception.getStackTrace());
            Log.error((action.getCookies().isSubAction()? "Sub": "") + "Action " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " failed!" + exception.getMessage());
        };
    }

    private <R, F extends ActionFuture<R, ?>> void Success(F actionFuture) {
        AbstractAction<R,?> action = actionFuture.getAction();
        if(actionFuture.getState().getMessage() != null) Log.info("Action " + action.getClass().getSimpleName() +"#"+ actionFuture.getAction().hashCode() + " " + actionFuture.getState().getMessage());
        Log.info((action.getCookies().isSubAction()? "Sub": "")+ "Action " +"was finished: " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " from " + action.getConfig().getISession().getExecutorName() + " in " + action.getCookies().getDuration() + "ms");

    }

    private <R, F extends ActionFuture<R, ?>> void Cancelled(F actionFuture) {
        AbstractAction<R,?> action = actionFuture.getAction();
        Log.info((action.getCookies().isSubAction()? "Sub": "") + "Action " +"was cancelled: " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " from " + action.getConfig().getISession().getExecutorName());
    }

    private <R, F extends ActionFuture<R, ?>> void Timeout(F actionFuture) {
        AbstractAction<R,?> action = actionFuture.getAction();
        if(actionFuture.getState().getMessage() != null) Log.info("Action " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " " + actionFuture.getState().getMessage());
        Log.info((action.getCookies().isSubAction()? "Sub": "") +"Action " +"has been timed out: " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " from " + action.getConfig().getISession().getExecutorName() + " in " + action.getCookies().getDuration() + "ms");
    }




    public <R> void startNextAction(AbstractAction<R,?>  action) {
        ActionFuture<?,?> nextActionFuture = action.getConfig().getISession().getPlayerAction().getNextActionFuture();
        if(nextActionFuture != null){
            nextActionFuture.execute();
        }
    }
}
