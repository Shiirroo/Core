package de.shiro.manager.mongo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import de.shiro.manager.manager.IManager;
import lombok.Getter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.HashMap;

import static com.mongodb.client.model.Filters.eq;

public class MongoManager implements IManager {

    @Getter
    private MongoClient mongoSyncClient;
    @Getter
    private MongoDatabase mongoDatabase;
    private final MongoConfig mongoConfig;

    private final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()

            ;

    @Getter
    private final Gson gson = builder.create();



    private final HashMap<Collections, MongoCollection<Document>> mongoCollections = new HashMap<>();


    public MongoManager(MongoConfig mongoConfig) {
        this.mongoConfig = mongoConfig;
    }

    public void connect() {
        CodecRegistry codecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        Codec<Document> documentCodec = codecRegistry.get(Document.class);
        codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromCodecs(documentCodec));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(mongoConfig.getConnectionString())
                .retryWrites(true)
                .codecRegistry(codecRegistry)
                .build();




        mongoSyncClient = MongoClients.create(settings);


        mongoDatabase = mongoSyncClient.getDatabase(mongoConfig.getDatabase());
        for(Collections collection : Collections.values()) {
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection.getName());
            if(collection.getIndex() != null) {
                IndexOptions options = new IndexOptions().unique(true);
                mongoCollection.createIndex(collection.getIndex(), options);
            }
            mongoCollections.put(collection,mongoCollection);
        }

    }

    public MongoCollection<Document> getCollection(Collections collection) {
        return mongoCollections.get(collection);
    }


    @Override
    public IManager init() {
        connect();
        return this;
    }

    @Override
    public IManager close() {
       this.mongoSyncClient.close();
        return this;
    }
}