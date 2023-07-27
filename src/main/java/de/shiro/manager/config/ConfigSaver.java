package de.shiro.manager.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class ConfigSaver<T extends IConfigBuilder> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    private final Logger logger = LoggerFactory.getLogger(ConfigSaver.class);
    private final T savedObject;

    public ConfigSaver(T savedObject){
        this.savedObject = savedObject;
    }



    public T saveConfig() {
        try (Writer writer = new FileWriter("./configs/" + savedObject.getClass().getSimpleName() + ".json")) {
            gson.toJson(savedObject, writer);
            return savedObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
