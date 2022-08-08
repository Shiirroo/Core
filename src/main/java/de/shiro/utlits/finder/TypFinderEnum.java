package de.shiro.utlits.finder;

public class TypFinderEnum<T extends Enum<T>> extends AbstractSuffixSearch<T>{


    public TypFinderEnum(Class<T> typeClass) {
        super(typeClass);
    }

    @Override
    protected void buildData() {
        for(T t : getTypeClass().getEnumConstants()) {
            add(t.name());
        }
    }

    @Override
    protected String getSearchKeyFor(T t) {
        return t.name();
    }

    @Override
    public String findBy(T filter) {
        return getSuffixes().stream().filter(s -> s.contains(filter.toString())).findFirst().orElse(null);
    }



}
