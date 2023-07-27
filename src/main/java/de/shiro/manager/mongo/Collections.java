package de.shiro.manager.mongo;


import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import de.shiro.record.RecordBin;
import de.shiro.record.RecordMeta;
import lombok.Getter;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

@Getter
public enum Collections {
    RECORD_BIN(RecordBin.class, RecordTimeBson()),
    RECORD_META(RecordMeta.class, RecordTimeBson()),


    ;
    @Getter
    private final Class<?> clazz;
    @Getter
    private final Bson index;


    Collections(Class<?> clazz, Bson index) {
        this.clazz = clazz;
        this.index = index;
    }


   public String getName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }


    public Collections getCollection(String name) {
        return Collections.valueOf(name);
    }

    public static Bson RecordTimeBson(){
        return Indexes.ascending("recordTime");
    }
}
