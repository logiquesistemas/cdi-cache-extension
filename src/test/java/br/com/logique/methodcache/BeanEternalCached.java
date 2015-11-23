package br.com.logique.methodcache;

import br.com.logique.methodcache.annotations.EternalCache;

import java.util.concurrent.TimeUnit;

/**
 * Created by Gustavo on 21/11/2015.
 */
public class BeanEternalCached {

    @EternalCache
    public double sin(int param1, int executionMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(executionMs);
        } catch (InterruptedException ex) {
        }
        return Math.sin(param1);
    }

}
