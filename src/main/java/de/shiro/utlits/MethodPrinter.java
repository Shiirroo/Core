package de.shiro.utlits;

import java.lang.reflect.Field;

public class MethodPrinter {

    public static void printMethods(Object obj) {
        Class<?> clazz = obj.getClass();
        System.out.println("READ FROM CLASS: " + clazz);
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (fieldValue != null) {
                System.out.println("[" + field.getType().getTypeName() + "] " +  field.getName() + ": " + fieldValue);
                //printMethods(fieldValue);
            }
        }
    }
}
