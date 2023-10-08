package io.github.eternalstone.common.toolkit.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象工具类
 *
 * @author Justzone on 2021/4/26 14:13
 */
@Slf4j
public class ObjectUtil {

    /**
     * 初始化值
     */
    public static void initInt(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.get(object) == null && (
                        "java.lang.Integer".equals(field.getType().getName()) ||
                        "java.lang.Long".equals(field.getType().getName()) ||
                        "java.lang.Short".equals(field.getType().getName()) ||
                        "java.lang.Byte".equals(field.getType().getName())
                        )) {
                    field.set(object, 0);
                }
            } catch (Exception e) {
                log.error("初始化int失败:", e);
            }
        }
    }

    /**
     * 对象转map
     */
    public static Map<String, Object> objToMap(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            String varName = field.getName();
            try {
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(obj);
                if (o != null) {
                    map.put(varName, o);
                }
                field.setAccessible(accessFlag);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                log.error("object转map失败:", ex);
            }
        }
        return map;
    }

}
