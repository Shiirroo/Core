package de.shiro.system.action.manager.event;

import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.utlits.Log;

public class ActionStartListener implements ActionStartEvent {

    @Override
    public <R, A extends ActionFuture<R, ?>> void onActionStart(A actionfuture) {
        actionfuture.getAction().getISession().getPlayerAction().setActionFuture(actionfuture);
        Log.info((actionfuture.getAction().getCookies().isSubAction()? "Sub": "") + "Action was started: " + actionfuture.getAction().getClass().getSimpleName() +"#"+ actionfuture.getAction().hashCode()+ " from " + actionfuture.getAction().getConfig().getISession().getExecutorName());
    }

}
