package de.shiro.record;

import com.google.gson.annotations.Expose;
import de.shiro.actions.recods.config.RecordGetPosActionConfig;
import de.shiro.actions.recods.config.RecordGetTypePosActionConfig;
import de.shiro.api.blocks.Area;
import de.shiro.record.records.RecordPosData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Records {

    @Getter
    private final long time;
    @Getter @Expose
    public List<RecordData> recordData;
    @Getter
    private boolean upload = false;

    public Records(long time){
        this.time = time;
        this.recordData = new ArrayList<>();
    }

    public Records(long time, RecordData recordData){
        this.time = time;
        this.recordData = new ArrayList<>(List.of(recordData));
    }

    public Records(long time, List<RecordData>  recordData){
        this.time = time;
        this.recordData = recordData;
    }
    public void addRecord(RecordData recordData){
        this.recordData.add(recordData);
    }

    private RecordBin toRecordBin(){
        return new RecordBin(time, recordData);
    }

    private RecordMeta toRecordMeta(){
        return new RecordMeta(time, recordData);
    }

    public void setUploaded(){
        this.upload = true;
    }

    public List<RecordPosData> getTimeFilterList(Area area){
        List<RecordPosData> recordPosData = new ArrayList<>();
        for(RecordData recordData : recordData){
            if(recordData instanceof RecordPosData posData){
                if(area.contains(posData.getPos())){
                    recordPosData.add(posData);
                }
            }
        }
        return recordPosData;
    }

    public List<RecordPosData> getTimeAreaFilterList(Area area, long from, long to){
        List<RecordPosData> recordPosData = new ArrayList<>();
        for(RecordData recordData : recordData){
            if(recordData instanceof RecordPosData posData){
                if(area.contains(posData.getPos())){
                    long sec = posData.getRecordTime() / 1000;
                    long timeFromSec = from  / 1000;
                    long timeToSec = to  / 1000;
                    if(sec >= timeFromSec && sec <= timeToSec){
                        recordPosData.add(posData);
                    }
                }
            }
        }
        return recordPosData;
    }

    public List<RecordPosData> getTimeFilterList(RecordGetPosActionConfig config){
        return getTimeAreaFilterList(config.getArea(), config.getTimefrom(), config.getTimeto());
    }

    public List<RecordPosData> getTimeTypeFilter(RecordGetTypePosActionConfig config){
        return getTimeTypeFilterList(config.getTimefrom(), config.getTimeto(), config.getRecordTyp());
    }

    public List<RecordPosData> getTimeTypeFilterList(long from, long to, RecordTyp typ){
        List<RecordPosData> recordPosData = new ArrayList<>();
        for(RecordData recordData : recordData){
            if(recordData instanceof RecordPosData posData && recordData.getRecordTyp().equals(typ)){
                    long sec = posData.getRecordTime() / 1000;
                    long timeFromSec = from  / 1000;
                    long timeToSec = to  / 1000;
                    if(sec >= timeFromSec && sec <= timeToSec){
                        recordPosData.add(posData);
                }
            }
        }
        return recordPosData;
    }



    public boolean upload(){
        return toRecordBin().create() && toRecordMeta().create();
    }

    @Override
    public String toString() {
        return "Records{" +
                "time=" + time +
                ", recordData=" + recordData +
                ", upload=" + upload +
                '}';
    }
}
