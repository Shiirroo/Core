package de.shiro.commands.commandbuilder.ckey;

import lombok.Getter;

public enum CommandSeparator {
    NO_SEPARATOR(""),
    COMMA(","),
    SPACE(" "),
    SEMICOLON(";"),
    COLON(":"),
    DASH("-"),
    UNDERSCORE("_"),
    PERCENT("%"),
    SLASH("/"),
    BACKSLASH("\\");


    @Getter
    private final String separator;

    CommandSeparator(String s) {
        this.separator = s;

    }
}
