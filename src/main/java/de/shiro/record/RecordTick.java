package de.shiro.record;

import de.shiro.Record;
import de.shiro.utlits.log.Log;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RecordTick extends TimerTask {

    @Getter
    private final ConcurrentHashMap<Long, Records> recordsConcurrent;
    @Getter
    private final SyncTime ticktime;
    @Getter
    private final long interval;

    public RecordTick(long interval){
        this.recordsConcurrent = new ConcurrentHashMap<>();
        ticktime = new SyncTime();
        this.interval = interval;
    }



    @Override
    public synchronized void run() {
        long time = ticktime.getSyncTime();
        ticktime.updateTick();
        if(recordsConcurrent.containsKey(time)) {
            Records records = recordsConcurrent.get(time);
            if(records.upload()){
                records.setUploaded();
                Log.info("UPLOAD" + " ( " + records.getRecordData().size() + " )" );
            }
        }
        Records lastRecord = lastRecords();
        if(lastRecord != null && lastRecord.isUpload()){
            recordsConcurrent.remove(lastRecord.getTime());
        }
    }

    public Records lastRecords(){
        long v = ticktime.getSyncTime() -  interval;
        return recordsConcurrent.get(v);
    }

    public Records getCurrent(){
        return recordsConcurrent.get(ticktime.getSyncTime());
    }

    public Records getNextRecord(){
        long v = ticktime.getSyncTime() + interval;
        return recordsConcurrent.get(v);
    }



    public synchronized void addRecords(RecordData record){
        SyncTime temp = new SyncTime(record.getRecordTime());
        if(recordsConcurrent.containsKey(temp.getSyncTime())){
            Records records = recordsConcurrent.get(temp.getSyncTime());
            records.addRecord(record);
        } else {
            recordsConcurrent.put(temp.getSyncTime(), new Records(temp.getSyncTime(), record));
        }
    }

    @Override
    public String toString() {
        return "RecordTick{" +
                "recordsConcurrent=" + recordsConcurrent +
                ", ticktime=" + ticktime +
                '}';
    }
}
