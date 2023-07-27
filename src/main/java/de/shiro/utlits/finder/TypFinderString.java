package de.shiro.utlits.finder;

import java.util.List;

public class TypFinderString<T> extends AbstractSuffixSearch<T> {

    public TypFinderString(List<T> suffixes) {
        super(suffixes);
    }

    @Override
    protected String getSearchKeyFor(T t) {
        return t.toString();
    }

    @Override
    public String findBy(T filter) {
        return getSuffixes().stream().filter(s -> s.toLowerCase().contains(filter.toString().toLowerCase())).findFirst().orElse(null);
    }

}
