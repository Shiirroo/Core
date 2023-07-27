package de.shiro.record;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import de.shiro.Record;
import de.shiro.api.blocks.Point3;
import de.shiro.api.blocks.Point3D;
import de.shiro.manager.mongo.Collections;
import de.shiro.record.records.RecordPosData;
import lombok.Getter;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

public class RecordMeta {

    @Getter @Expose
    private final long recordTime;
    @Getter @Expose
    private final Set<RecordTyp> recordTyps;
    @Getter @Expose
    private final Set<UUID> causeUserIDs;
    @Getter @Expose
    private final HashMap<String, Set<Point3>> regions;


    public RecordMeta(long time, List<RecordData> recordDataList){
        this.recordTime = time;
        if(recordDataList.isEmpty()) throw new RuntimeException("ERROR");
        this.recordTyps =  getRecordTyps(recordDataList);
        this.causeUserIDs = getCauseUserIDs(recordDataList);
        this.regions = getRegions(recordDataList);
    }



    private Set<RecordTyp> getRecordTyps(List<RecordData> recordDataList){
        return recordDataList.stream().map(RecordData::getRecordTyp).collect(Collectors.toSet());
    }



    private Set<UUID> getCauseUserIDs(List<RecordData> recordDataList){
        return recordDataList.stream().map(RecordData::getCauseUserID).collect(Collectors.toSet());
    }

    private HashMap<String, Set<Point3>> getRegions(List<RecordData> recordDataList){
        HashMap<String, Set<Point3>> temp  = new HashMap<>();
        for(RecordData recordData: recordDataList){
            if(recordData instanceof RecordPosData recordPosData) {
                String worldName = recordData.getWorldName();
                if (worldName != null) {
                    if (temp.containsKey(worldName)) {
                        Set<Point3> tempSet = temp.get(worldName);
                        if (!tempSet.contains(recordPosData.getPos())){
                            tempSet.add(recordPosData.getPos());
                            temp.put(worldName, tempSet);
                        }
                    } else {
                        Set<Point3> point3DS = new HashSet<>();
                        point3DS.add(recordPosData.getPos());
                        temp.put(worldName, point3DS);
                    }
                }
            }

        }
        return temp;
    }

    public boolean create() {
        return Record.getManager().getMongoManager().getCollection(Collections.RECORD_META).insertOne(toDocument()).wasAcknowledged();
    }

    public static RecordMeta ofGson(Document document) throws JsonSyntaxException {
        Gson gson = Record.getManager().getMongoManager().getGson();
        return gson.fromJson(gson.toJson(document), RecordMeta.class);
    }

    public Document toDocument() {
        Gson gson = Record.getManager().getMongoManager().getGson();
        return gson.fromJson(gson.toJson(this), Document.class);
    }


    @Override
    public String toString() {
        return "RecordMeta{" +
                "recordTime=" + recordTime +
                ", recordTyps=" + recordTyps +
                ", causeUserIDs=" + causeUserIDs +
                ", regions=" + regions +
                '}';
    }
}
