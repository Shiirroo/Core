package de.shiro.commands.commandbuilder.ckey;

import de.shiro.commands.commandbuilder.ckey.syntax.CKeySyntax;
import lombok.Getter;

public enum CKey {
    Name("[Name]", CKeySyntax.CUSTOM, CKeyClasses.STRING),
    Amount("[0-9]", CKeySyntax.CUSTOM, CKeyClasses.INT),
    Page("[0-9]", CKeySyntax.PAGE, CKeyClasses.INT),
    ChunkPoint("[X,Z]", CKeySyntax.CHUNKCOORDINATES, CKeyClasses.CHUNKPOINT),
    ChunkFrom("[X,Z]", CKeySyntax.CHUNKCOORDINATES, CKeyClasses.CHUNKPOINT),
    ChunkTo("[X,Z]", CKeySyntax.CHUNKCOORDINATES, CKeyClasses.CHUNKPOINT),
    Point3("[X,Y,Z]", CKeySyntax.POINT3, CKeyClasses.POINT3),
    Material("[Material]", CKeySyntax.MATERIAL, CKeyClasses.MATERIAL),
    Visibility("[Visibility]", CKeySyntax.VISIBILITY, CKeyClasses.ENUM),
    PlayerActions("[PlayerActions]", CKeySyntax.ACTIONS, CKeyClasses.STRING),
    TimeTo("[*w*d*h*m*s]", CKeySyntax.TIMES, CKeyClasses.STRING),
    TimeFrom("[*w*d*h*m*s]", CKeySyntax.TIMES, CKeyClasses.STRING),
    RANGE("[1-50]", CKeySyntax.RANGES, CKeyClasses.INT),
    LOGCONFIG("[STATE]", CKeySyntax.LOGSTATES, CKeyClasses.ENUM),
    RECORDTYP("[STATE]", CKeySyntax.RECORD, CKeyClasses.ENUM),

    ;
    @Getter
    private final String key;
    @Getter
    private final CKeySyntax cKeySyntax;
    @Getter
    private final CKeyClasses cKeyClasses;


    CKey(String key, CKeySyntax cKeySyntax, CKeyClasses cKeyClasses){
        this.key = key;
        this.cKeySyntax = cKeySyntax;
        this.cKeyClasses = cKeyClasses;
    }


}
