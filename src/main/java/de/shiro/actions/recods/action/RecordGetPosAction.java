package de.shiro.actions.recods.action;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import de.shiro.Record;
import de.shiro.actions.recods.config.RecordGetPosActionConfig;
import de.shiro.api.blocks.Point3;
import de.shiro.manager.mongo.Collections;
import de.shiro.record.*;
import de.shiro.record.records.RecordPosData;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.AbstractGetAction;
import de.shiro.system.config.AbstractPosActionConfig;
import de.shiro.system.config.AbstractRecordActionConfig;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Filters.or;

public class RecordGetPosAction extends AbstractGetAction<RecordGetPosActionConfig> {

    public RecordGetPosAction(RecordGetPosActionConfig config) {
        super(config);
    }

    @Override
    public ActionResult<List<RecordPosData>> execute() throws Exception {
        List<RecordPosData> recordPosData = new ArrayList<>();

        FindIterable<Document> metas = getRecordMetas();
        Iterator<Document> metaIterator = metas.iterator();

        if(metaIterator.hasNext()) recordPosData.addAll(getRecordPosData(metaIterator));

        recordPosData.addAll(getLocalRecordPosData());

        return ActionResult.of(recordPosData);
    }


    private FindIterable<Document> getRecordMetas(){
        long interval = getConfig().getRecordManager().getInterval();

        MongoCollection<Document> recordMetaCollection = Record.getManager().getMongoManager().getCollection(Collections.RECORD_META);
        String fieldName = "regions."+getConfig().getWorldName();
        long minTime = SyncTime.of(getConfig().getTimefrom()- interval).getSyncTime();
        long maxTime = SyncTime.of(getConfig().getTimeto() + interval).getSyncTime();
        Bson postionBson = getBson(minTime, maxTime, fieldName);
        return recordMetaCollection.find(postionBson);
    }

    private Bson getBson(long minTime, long maxTime, String fieldName) {
        Bson Time = Filters.and(
                Filters.gte("recordTime", minTime),
                Filters.lte("recordTime", maxTime)
        );
        Point3 min = getConfig().getArea().getMin();
        Point3 max = getConfig().getArea().getMax();

        Bson PostionTime = Filters.and(
                Filters.and(gte(fieldName +".x" , min.getX()), lte(fieldName +".x" , max.getX())),
                Filters.and(gte(fieldName +".z" , min.getZ()), lte(fieldName +".z" , max.getZ())));

        return Filters.and(Time, PostionTime);
    }


    private List<RecordPosData> getRecordPosData(Iterator<Document> metaIterator){
        List<Bson> bsonTimeFilter = new ArrayList<>();

        while (metaIterator.hasNext()){
            Document document = metaIterator.next();
            RecordMeta recordMeta = RecordMeta.ofGson(document);
            bsonTimeFilter.add(eq("recordTime", recordMeta.getRecordTime()));
        }
        MongoCollection<Document> recordBin = Record.getManager().getMongoManager().getCollection(Collections.RECORD_BIN);
        FindIterable<Document> recordFindIterable = recordBin.find(or(bsonTimeFilter));
        Iterator<Document> recordIterator = recordFindIterable.iterator();
        List<RecordPosData> recordPosData   = new ArrayList<>();
        while (recordIterator.hasNext()) {
            Document document = recordIterator.next();
            RecordBin bin = RecordBin.ofGson(document);
            List<RecordData> recordDataList = bin.bytesToList();
            Records re = new Records(bin.getRecordTime(), recordDataList);
            recordPosData.addAll(re.getTimeFilterList(getConfig()));
        }
        return recordPosData;
    }


    private List<RecordPosData> getLocalRecordPosData(){
        RecordTick recordTick = getConfig().getRecordManager().getRecordTick();
        Records currentRecord = recordTick.getCurrent();
        Records lastRecords = recordTick.lastRecords();
        Records next = recordTick.getNextRecord();
        List<RecordPosData> posData = new ArrayList<>();
        if(lastRecords != null ) if(!lastRecords.isUpload()) posData.addAll(lastRecords.getTimeFilterList(getConfig()));
        if(currentRecord != null ) if(!currentRecord.isUpload()) posData.addAll(currentRecord.getTimeFilterList(getConfig()));
        if(next != null) if(!next.isUpload()) posData.addAll(next.getTimeFilterList(getConfig()));
        return posData;
    }

}
