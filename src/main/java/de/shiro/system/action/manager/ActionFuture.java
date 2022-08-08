package de.shiro.system.action.manager;

import de.shiro.Core;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.event.ActionFinishedState;
import de.shiro.utlits.Config;
import de.shiro.utlits.Log;
import de.shiro.utlits.TraceHelper;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.*;

public class ActionFuture<R, A extends AbstractAction<R, ?>> {

    @Getter
    private final UUID futureID = UUID.randomUUID();

    private R result;

    @Getter
    private final A action;

    @Getter
    private Future<ActionResult<R>> future;

    @Getter
    private ActionFinishedState state = ActionFinishedState.Unknown;

    public ActionFuture(A action) {
        this.action = action;

    }

    public void execute() {
        if(action.getISession().getPlayerAction().hasActionFuture()){
            if(!action.getISession().getPlayerAction().getActionFuture().equals(this) && !action.getISession().equals(Config.getServerSession()) && !action.getCookies().isSubAction()){
                action.getISession().getPlayerAction().addActionFutureQueue(this);
                Log.info("Action was added to queue: " + action.getClass().getSimpleName() +action.getClass().getSimpleName() +"#"+ action.hashCode()+ " from " + action.getConfig().getISession().getExecutorName());
                return;
            }
        }
        result = (R) getResult();
    }

    public ActionResult<R> getResult() {
        if(result != null) return (ActionResult<R>) result;
        ActionResult<R> actionResult = null;
        action.getCookies().updateStartTime();
        Core.getActionEventManager().getActionStartListener().onActionStart(this);
        try {
            future = Config.getService().submit(action::execute);
            actionResult = future.get();
            state = ActionFinishedState.Success;
        } catch (InterruptedException | ExecutionException e) {
            state = ActionFinishedState.Failed;
        }
        Core.getActionEventManager().getActionFinishedListener().onActionFinished(this);
        return actionResult;
    }


    public ActionResult<R> getResult(TimeUnit timeUnit, long timeout) {
        if(result != null) return (ActionResult<R>) result;
        ActionResult<R> actionResult = null;
        action.getCookies().updateStartTime();
        Core.getActionEventManager().getActionStartListener().onActionStart(this);
        try {
            future = Config.getService().submit(action::execute);
            actionResult = future.get(timeout, timeUnit);
            state = ActionFinishedState.Success;
        } catch (TimeoutException e) {
            cancel();
            future.cancel(true);
            state = ActionFinishedState.Timeout;
        } catch (InterruptedException | ExecutionException e) {
            state = ActionFinishedState.Failed;
        }
        Core.getActionEventManager().getActionFinishedListener().onActionFinished(this);
        return actionResult;
    }

    public boolean cancel() {
        if(getFuture() == null) return false;
        Log.error(Thread.currentThread().getName() + ": " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " is cancelling...");
        boolean cancel = getFuture().isCancelled();
        if(!action.getCookies().getSubActions().isEmpty()) action.getCookies().reset();
        return cancel;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ActionFuture) {
            return ((ActionFuture<?, ?>) obj).getFutureID().equals(this.getFutureID());
        }
        return false;
    }

    public static <R, A extends AbstractAction<R, ?>> ActionFuture<R,A> of(A action) {
        return new ActionFuture<>(action);
    }
}
