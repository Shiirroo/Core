package de.shiro.utlits.finder;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSuffixSearch<T> {

    @Getter
    private Class<T> typeClass;
    @Getter
    private final List<String> suffixes = new ArrayList<>();

    public AbstractSuffixSearch(Class<T> typeClass) {
        this.typeClass = typeClass;
        buildData();
    }

    public AbstractSuffixSearch(List<T> suffixes) {
        buildData(suffixes);
    }

    protected void buildData() {
        add(typeClass.toString());
    }

    protected void buildData(List<T> suffixes) {
        addAll(suffixes);
    }

    protected String getSearchKeyFor(T t) {
        return t.toString();
    }

    protected void add(String suffix) {
        suffixes.add(suffix);
    }

    protected void addAll(List<T> suffixes) {
        suffixes.forEach(s -> this.suffixes.add(s.toString()));
    }

    public String findBy(String filter) {
        return suffixes.stream().filter(s -> s.equalsIgnoreCase(filter)).findFirst().orElse(null);
    }

    public String findBy(T filter) {
        return suffixes.stream().filter(s -> s.equals(filter)).findFirst().orElse(null);
    }

    public List<String> getSuffixContains(String filter) {
        return suffixes.stream().filter(s -> s.toLowerCase().contains(filter.toLowerCase())).sorted(String::compareTo).collect(Collectors.toList());
    }
}
