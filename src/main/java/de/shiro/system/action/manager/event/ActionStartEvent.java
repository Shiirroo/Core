package de.shiro.system.action.manager.event;

import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;

public interface ActionStartEvent extends ActionEvent {

    <R, A extends ActionFuture<R, ?>> void onActionStart(A actionType);



}
