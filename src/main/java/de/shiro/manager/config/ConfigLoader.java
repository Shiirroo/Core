package de.shiro.manager.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.shiro.Record;
import de.shiro.record.RecordBin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class ConfigLoader<T extends IConfigBuilder> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    private final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private final Class<T> tClass;

    public ConfigLoader(Class<T> tClass){
        this.tClass = tClass;
    }





    public T loadConfig() {
        File file = new File("./configs/" + tClass.getSimpleName() + ".json");
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                return gson.fromJson(reader, tClass);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (com.google.gson.JsonSyntaxException | com.google.gson.JsonIOException c){
                return getConfig();
            }
        } else {
            return getConfig();
        }
    }


    private T getConfig() {
        try {
            T newInstance = tClass.getConstructor().newInstance();
            ConfigSaver<T> configSaver = new ConfigSaver<>(newInstance);
            return configSaver.saveConfig();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }


}
