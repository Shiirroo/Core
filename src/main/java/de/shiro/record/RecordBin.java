package de.shiro.record;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import de.shiro.Record;
import de.shiro.manager.mongo.Collections;
import de.shiro.utlits.CompressionUtils;
import lombok.Getter;
import org.bson.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RecordBin implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter @Expose
    private final long recordTime;
    private byte[] originalBytes;
    @Expose
    private final byte[] bytes;


    public RecordBin(long recordTime, List<RecordData> data) {
        this.recordTime = recordTime;
        originalBytes = listToBytes(data);
        bytes = CompressionUtils.compress(originalBytes);
    }

    public byte[] listToBytes(List<RecordData> recordDataList) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(recordDataList);
            objectOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getOriginalBytes() {
        if (originalBytes == null) {
            originalBytes = CompressionUtils.decompress(bytes);
            return originalBytes;
        } else {
            return originalBytes;
        }
    }

    public List<RecordData> bytesToList() {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getOriginalBytes());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object readObject = objectInputStream.readObject();
            List<RecordData> objectList = new ArrayList<>();
            if(readObject instanceof List<?> list){
                objectList.addAll(list.stream().filter(object -> object instanceof RecordData).map(object -> (RecordData) object).toList());
            }
            objectInputStream.close();
            byteArrayInputStream.close();
            return objectList;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "RecordBin{" +
                "recordTime=" + recordTime +
                ", originalBytes=" + (bytes != null? bytes.length : "0") +
                ", bytes=" + (bytes != null? bytes.length : "0") +
                '}';
    }


    public boolean create() {
        return Record.getManager().getMongoManager().getCollection(Collections.RECORD_BIN).insertOne(toDocument()).wasAcknowledged();
    }

    public static RecordBin ofGson(Document document) throws JsonSyntaxException {
        Gson gson = Record.getManager().getMongoManager().getGson();
        return gson.fromJson(gson.toJson(document), RecordBin.class);
    }

    public Document toDocument() {
        Gson gson = Record.getManager().getMongoManager().getGson();
        return gson.fromJson(gson.toJson(this), Document.class);
    }
}
