package de.shiro.actions.recods.action;

import de.shiro.Record;
import de.shiro.actions.recods.config.RecordChangeConfigActionConfig;
import de.shiro.manager.config.ConfigSaver;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.utlits.log.LogManager;

public class RecordChangeConfigActionAction extends AbstractAction<Boolean, RecordChangeConfigActionConfig> {

    public RecordChangeConfigActionAction(RecordChangeConfigActionConfig config) {
        super(config);
    }

    @Override
    public ActionResult<Boolean> execute() throws Exception {
        LogManager config = Record.getManager().getLogManager();
        config.changeState(getConfig().getNewState());
        new ConfigSaver<>(config).saveConfig();
        return ActionResult.Success();
    }


}
