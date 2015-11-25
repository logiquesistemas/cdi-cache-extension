package br.com.logique.methodcache;

import br.com.logique.methodcache.annotations.EternalCache;
import br.com.logique.methodcache.annotations.TimedCache;

import java.util.concurrent.TimeUnit;

/**
 * Created by Gustavo on 25/11/2015.
 */
public class MultiplesCacheBean {

    @EternalCache
         public double pow2(int param1, int executionMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(executionMs);
        } catch (InterruptedException ex) {
        }
        return Math.pow(param1, 2);
    }

    @EternalCache
    public double pow3(int param1, int executionMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(executionMs);
        } catch (InterruptedException ex) {
        }
        return Math.pow(param1, 3);
    }

    @EternalCache
    public double pow4(int param1, int executionMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(executionMs);
        } catch (InterruptedException ex) {
        }
        return Math.pow(param1, 4);
    }

}
