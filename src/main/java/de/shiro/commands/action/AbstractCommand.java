package de.shiro.commands.action;

import de.shiro.system.action.manager.facede.FacedInternal;
import lombok.Getter;

public class AbstractCommand {

    public final FacedInternal facede;

    public AbstractCommand(FacedInternal facedInternal) {
        this.facede = facedInternal;
    }






}
