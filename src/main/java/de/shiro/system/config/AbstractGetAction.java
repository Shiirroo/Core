package de.shiro.system.config;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import de.shiro.Record;
import de.shiro.actions.recods.config.RecordActionConfig;
import de.shiro.api.blocks.Point3;
import de.shiro.manager.mongo.Collections;
import de.shiro.record.*;
import de.shiro.record.records.RecordPosData;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public abstract class AbstractGetAction<T extends AbstractRecordActionConfig> extends AbstractAction<List<RecordPosData>, T> {

    public AbstractGetAction(T config) {
        super(config);
    }


}
