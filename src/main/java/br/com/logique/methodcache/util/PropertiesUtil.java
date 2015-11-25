package br.com.logique.methodcache.util;

/**
 * Created by Gustavo on 25/11/2015.
 */
public class PropertiesUtil {

    public static long getLongProperty(String key, long defaultValue) {
        return Long.parseLong(System.getProperty(key, String.valueOf(defaultValue)));
    }

}
