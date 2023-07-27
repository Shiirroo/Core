package de.shiro.manager.manager;

import com.mongodb.ExplainVerbosity;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import de.shiro.Record;
import de.shiro.actions.recods.config.RecordActionConfig;
import de.shiro.api.blocks.Area;
import de.shiro.api.blocks.Point3;
import de.shiro.manager.mongo.Collections;
import de.shiro.record.*;
import de.shiro.record.records.RecordPosData;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.*;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.*;

public class RecordManager implements IManager {

    @Getter
    private final RecordTick recordTick;
    @Getter
    private final Timer timer;
    @Getter
    private final long interval = 10000;


    public RecordManager(){
        this.timer = new Timer();
        recordTick = new RecordTick(10000);
    }

    private void setTimerTask(){
        timer.scheduleAtFixedRate(recordTick, 0, interval);
    };




    @Override
    public IManager init() {
        setTimerTask();
        return this;
    }

    @Override
    public IManager close() {
        timer.cancel();
        return this;
    }
}
