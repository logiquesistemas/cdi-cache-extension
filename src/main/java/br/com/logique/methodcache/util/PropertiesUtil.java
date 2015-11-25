package br.com.logique.methodcache.util;

/**
 * Created by Gustavo on 25/11/2015.
 */
public class PropertiesUtil {

    public static int getIntProperty(String key, int defaultValue) {
        return Integer.parseInt(System.getProperty(key, String.valueOf(defaultValue)));
    }

}
