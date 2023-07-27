package de.shiro.system.option;

import lombok.Getter;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Options<O extends Enum<O>> {

    @Getter
    private final Map<String, Option<O, ?>> byNames = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    @Getter
    private final Map<O, Option<O, ?>> byType = new HashMap<>();

    protected <T extends Option<O, ?>> T register(final T option){
        byNames.put(option.getName(), option);
        byType.put(option.getType(), option);
        return option;
    }








}
