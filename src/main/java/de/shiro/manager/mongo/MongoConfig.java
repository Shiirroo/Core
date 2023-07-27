package de.shiro.manager.mongo;


import com.google.gson.annotations.Expose;
import com.mongodb.ConnectionString;
import de.shiro.manager.config.IConfigBuilder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class MongoConfig extends IConfigBuilder implements Serializable {

    @Expose
    private final String hostname;
    @Expose
    private final int port;
    @Expose
    private final String database;
    @Expose
    private final String user;
    @Expose
    private final String password;
    @Expose
    private final String extra;


    public MongoConfig(String hostname, int port, String database, String user, String password, String extra){
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.extra = extra;
    }

    public MongoConfig(){
        this("localhost", 27017, "default_database", "root", "none", "");
    }


    public String getConnectionStringFormat() {
        return String.format("mongodb://%s:%s@%s:%d/%s", user, password, hostname, port, database);
    }

    public ConnectionString getConnectionString() {
        return new ConnectionString("mongodb://" + user + ":" + password + "@" + hostname + ":" + port + "/" + extra);
    }


}
