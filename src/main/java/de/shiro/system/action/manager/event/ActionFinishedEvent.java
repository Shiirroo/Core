package de.shiro.system.action.manager.event;

import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;

public interface ActionFinishedEvent extends ActionEvent {

    <R, A extends ActionFuture<R, ?>> void onActionFinished(A actionFuture);
}
