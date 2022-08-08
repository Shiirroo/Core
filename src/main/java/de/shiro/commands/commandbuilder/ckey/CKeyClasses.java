package de.shiro.commands.commandbuilder.ckey;

import de.shiro.api.blocks.ChunkPoint;
import de.shiro.api.blocks.Point2;
import de.shiro.api.blocks.Point3;
import de.shiro.api.blocks.Point3D;
import lombok.Getter;
import org.bukkit.Material;

public enum CKeyClasses {
    STRING(String.class, CommandSeparator.NO_SEPARATOR,String.class),
    INT(Integer.class, CommandSeparator.NO_SEPARATOR,int.class),
    ENUM(Enum.class, CommandSeparator.NO_SEPARATOR,String.class),
    CHUNKPOINT(ChunkPoint .class, CommandSeparator.COMMA,int.class, int.class),
    MATERIAL(Material.class, CommandSeparator.NO_SEPARATOR,String.class),
    POINT2(Point2.class, CommandSeparator.COMMA,int.class, int.class),
    POINT3(Point3.class, CommandSeparator.COMMA,int.class, int.class, int.class),
    POINT3D(Point3D.class, CommandSeparator.COMMA,double.class, double.class, double.class);




    @Getter
    private final CommandSeparator commandSeparator;
    @Getter
    private final Class<?> objectClass;
    @Getter
    private final Class<?>[] parameterTypes;


    CKeyClasses(Class<?> objectClass, CommandSeparator separator, Class<?>... parameterTypes) {
        this.objectClass = objectClass;
        this.commandSeparator = separator;
        this.parameterTypes = parameterTypes;
    }




}
