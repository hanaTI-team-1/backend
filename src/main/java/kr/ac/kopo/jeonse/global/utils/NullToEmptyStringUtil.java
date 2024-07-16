package kr.ac.kopo.jeonse.global.utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class NullToEmptyStringUtil {
    public void replaceNullsWithEmptyStrings(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getType().equals(String.class) && field.get(obj) == null) {
                    field.set(obj, "");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
