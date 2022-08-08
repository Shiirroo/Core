package de.shiro.system.action;

import de.shiro.system.action.manager.ActionFuture;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerAction {

    @Getter @Setter
    private ActionFuture<?, ?> actionFuture = null;

    @Getter @Setter
    private Queue<ActionFuture<?,?>> actionFutureQueue = new LinkedList<>();


    public void reset() {
        if(actionFuture != null) {
            actionFuture.cancel();
            actionFuture = null;
        }
        if(!actionFutureQueue.isEmpty()) clearActionQueue();
    }

    public void addActionFutureQueue(ActionFuture<?,?> actionFuture) {
        this.actionFutureQueue.add(actionFuture);
    }

    public ActionFuture<?,?> getNextActionFuture() {
        if(this.actionFutureQueue.isEmpty()) {
            actionFuture = null;
            return null;
        }
        this.actionFuture = this.actionFutureQueue.poll();
        return actionFuture;
    }

    public void clearActionQueue() {
       if(!actionFutureQueue.isEmpty()) {
           actionFutureQueue.forEach(ActionFuture::cancel);
           actionFutureQueue.clear();
       }
    }

    public boolean isActionFutureQueueEmpty() {
        return this.actionFutureQueue.isEmpty();
    }
    public boolean hasActionFuture() {
        return this.actionFuture != null;
    }


}
