package de.shiro.record;

import de.shiro.utlits.Utlits;
import lombok.Getter;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class SyncTime {

    private final AtomicLong time;

    public SyncTime(){
        this.time = new AtomicLong(getNewTick(System.currentTimeMillis()));
    }

    public SyncTime(Long time){
        this.time = new AtomicLong(getNewTick(time));
    }

    public synchronized void updateTick(){
        this.time.set(getNewTick(System.currentTimeMillis()));
    }

    private synchronized long getNewTick(long time){
        return Utlits.zeroLastNDigits(time, 4);
    }

    public synchronized long getSyncTime(){
        return time.get();
    }

    public synchronized static SyncTime of(Long time){
        return new SyncTime(time);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SyncTime syncTime = (SyncTime) object;
        return Objects.equals(time.get(), syncTime.time.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(time.get());
    }
}
