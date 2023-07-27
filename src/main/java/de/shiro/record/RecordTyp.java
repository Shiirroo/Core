package de.shiro.record;

import de.shiro.Record;
import de.shiro.record.records.*;
import lombok.Getter;

public enum RecordTyp {
    PLAYER_JOIN(PlayerJoinRecord.class),
    PLAYER_LEAVE(PlayerLeaveRecord.class),
    PLAYER_BREAK_BLOCK(BlockBreakRecord.class),
    PLAYER_PLACE_BLOCK(BlockPlaceRecord.class),
    DROP_ITEM(DropItemRecord.class),

    ;
    @Getter
    private final Class<? extends RecordData> recordDataClass;

    RecordTyp(Class<? extends RecordData> recordDataClass){
        this.recordDataClass = recordDataClass;
    }


}
