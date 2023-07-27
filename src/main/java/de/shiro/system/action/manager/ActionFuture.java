package de.shiro.system.action.manager;

import de.shiro.Record;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.event.ActionFinishedState;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.utlits.Config;
import de.shiro.utlits.log.Log;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.*;

public class ActionFuture<R, A extends AbstractAction<R, ? extends AbstractActionConfig>> implements Runnable {

    @Getter
    private final UUID futureID = UUID.randomUUID();

    private ActionResult<R> result;
    @Getter
    private final A action;
    @Getter
    private ActionFinishedState state = ActionFinishedState.Unknown;
    @Getter
    private boolean isDone;
    @Getter
    private boolean isCancelled;
    @Getter
    private final CompletableFuture<ActionResult<R>> future = new CompletableFuture<>();


    public ActionFuture(A action) {
        this.action = action;
        this.isDone = false;
        this.isCancelled = false;

    }

    public void execute() {
        if(action.getISession().getPlayerAction().hasActionFuture() && !action.getCookies().isSkipQue()){
            if(!action.getISession().getPlayerAction().getActionFuture().equals(this) && !action.getISession().equals(Config.getServerSession()) && !action.getCookies().isSubAction()){
                action.getISession().getPlayerAction().addActionFutureQueue(this);
                Log.info("Action was added to queue: " + action.getClass().getSimpleName() +action.getClass().getSimpleName() +"#"+ action.hashCode()+ " from " + action.getConfig().getISession().getExecutorName());
                return;
            }
        }
        try {
            result = getActionResult();
            isDone = true;
            future.complete(result);
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
    }

    private ActionResult<R> getActionResult() {
        if (result != null) return result;
        ActionResult<R> actionResult = null;
        Record.getManager().getActionEventManager().getActionStartListener().onActionStart(this);
        try {
            actionResult = action.execute();
            state = ActionFinishedState.Success;
        } catch (Exception e) {
            state = ActionFinishedState.Failed;
            state.setMessage(e);
        }
        Record.getManager().getActionEventManager().getActionFinishedListener().onActionFinished(this);
        return actionResult;
    }




    public void cancel() {
        if(getFuture() == null) return;
        this.isCancelled = true;
        Log.error(Thread.currentThread().getName() + ": " + action.getClass().getSimpleName() +"#"+ action.hashCode() + " is cancelling...");
        if(!action.getCookies().getSubActions().isEmpty()) action.getCookies().reset();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ActionFuture) {
            return ((ActionFuture<?, ?>) obj).getFutureID().equals(this.getFutureID());
        }
        return false;
    }

    public static <R, A extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R,A> of(A action) {
        return new ActionFuture<>(action);
    }

    @Override
    public void run() {
        execute();
        ScheduledExecutorService timeoutExecutor = Executors.newSingleThreadScheduledExecutor();
        timeoutExecutor.schedule(() -> {
            if (!future.isDone()) {
                state = ActionFinishedState.Timeout;
                future.cancel(true);
                System.out.println("Action execution timed out and was cancelled.");
            }
        }, 1, TimeUnit.MINUTES);
    }
}
