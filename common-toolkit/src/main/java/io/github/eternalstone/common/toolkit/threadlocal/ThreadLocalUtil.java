package io.github.eternalstone.common.toolkit.threadlocal;

import java.util.HashMap;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 11:09
 */
public class ThreadLocalUtil {

    private static final ThreadLocal<LocalFiled> THREAD_LOCAL = new ThreadLocal<>();

    private ThreadLocalUtil() {
    }

    private static LocalFiled get() {
        if (THREAD_LOCAL.get() == null) {
            synchronized (THREAD_LOCAL) {
                if (THREAD_LOCAL.get() == null) {
                    LocalFiled localFiled = new LocalFiled();
                    localFiled.setProperties(new HashMap<>(4));
                    THREAD_LOCAL.set(localFiled);
                }
            }
        }
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }


    public static LocalFiled getLocalFiled() {
        return get();
    }

    public static void setLocalFiled(LocalFiled localFiled) {
        THREAD_LOCAL.set(localFiled);
    }

    public static void setProperty(String key, String value) {
        get().getProperties().put(key, value);
    }


    public static String getProperty(String key) {
        return get().getProperties().get(key);
    }

}
