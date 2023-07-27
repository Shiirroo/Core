package de.shiro.actions.recods.action;

import com.mongodb.client.FindIterable;
import de.shiro.Record;
import de.shiro.actions.recods.config.RecordActionConfig;
import de.shiro.actions.recods.config.RecordAddActionConfig;
import de.shiro.api.blocks.Area;
import de.shiro.record.SyncTime;
import de.shiro.record.records.RecordPosData;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import org.bson.Document;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecordAddAction extends AbstractAction<Boolean, RecordAddActionConfig> {

    public RecordAddAction(RecordAddActionConfig config) {
        super(config);
    }

    @Override
    public ActionResult<Boolean> execute() throws Exception {
        if(getConfig().getRecord() == null) return ActionResult.Failed();
        getConfig().getRecordManager().getRecordTick().addRecords(getConfig().getRecord());
        return ActionResult.Success();
    }


}
