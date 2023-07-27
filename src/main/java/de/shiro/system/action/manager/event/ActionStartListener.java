package de.shiro.system.action.manager.event;

import de.shiro.system.action.manager.ActionFuture;
import de.shiro.utlits.log.Log;

public class ActionStartListener implements ActionStartEvent {

    @Override
    public <R, A extends ActionFuture<R, ?>> void onActionStart(A actionFuture) {
        actionFuture.getAction().getCookies().setStartTime();
        if(!actionFuture.getAction().getCookies().isSkipQue()) actionFuture.getAction().getISession().getPlayerAction().setActionFuture(actionFuture);
        Log.info((actionFuture.getAction().getCookies().isSubAction()? "Sub": "") + "Action was started: " + actionFuture.getAction().getClass().getSimpleName() +"#"+ actionFuture.getAction().hashCode()+ " from " + actionFuture.getAction().getConfig().getISession().getExecutorName());
    }

}
