package de.shiro.actions.recods.action;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import de.shiro.Record;
import de.shiro.actions.recods.config.RecordGetTypePosActionConfig;
import de.shiro.manager.mongo.Collections;
import de.shiro.record.*;
import de.shiro.record.records.RecordPosData;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.config.AbstractGetAction;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class RecordGetTypeAction extends AbstractGetAction<RecordGetTypePosActionConfig> {

    public RecordGetTypeAction(RecordGetTypePosActionConfig config) {
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
        long minTime = SyncTime.of(getConfig().getTimefrom()- interval).getSyncTime();
        long maxTime = SyncTime.of(getConfig().getTimeto() + interval).getSyncTime();
        Bson postionBson = getBson(minTime, maxTime);
        return recordMetaCollection.find(postionBson);
    }

    private Bson getBson(long minTime, long maxTime) {
        Bson Time = Filters.and(
                Filters.gte("recordTime", minTime),
                Filters.lte("recordTime", maxTime)
        );

        Bson Type = Filters.in("recordTyps", getConfig().getRecordTyp());


        return Filters.and(Time, Type);
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
            recordPosData.addAll(re.getTimeTypeFilter(getConfig()));
        }
        return recordPosData;
    }


    private List<RecordPosData> getLocalRecordPosData(){
        RecordTick recordTick = getConfig().getRecordManager().getRecordTick();
        Records currentRecord = recordTick.getCurrent();
        Records lastRecords = recordTick.lastRecords();
        Records next = recordTick.getNextRecord();
        List<RecordPosData> posData = new ArrayList<>();
        if(lastRecords != null ) if(!lastRecords.isUpload()) posData.addAll(lastRecords.getTimeTypeFilter(getConfig()));
        if(currentRecord != null ) if(!currentRecord.isUpload()) posData.addAll(currentRecord.getTimeTypeFilter(getConfig()));
        if(next != null) if(!next.isUpload()) posData.addAll(next.getTimeTypeFilter(getConfig()));
        return posData;
    }

}
