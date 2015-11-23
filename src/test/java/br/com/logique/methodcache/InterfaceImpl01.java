package br.com.logique.methodcache;

import br.com.logique.methodcache.annotations.TimedCache;

import java.util.concurrent.TimeUnit;

/**
 * Created by Gustavo on 19/11/2015.
 */
public class InterfaceImpl01 implements Interface {

    @Override
    public double pow2(int value, int timeMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeMs);
        } catch (InterruptedException ex) {
        }
        return Math.pow(value, 2);
    }

    @Override
    @TimedCache(unit = TimeUnit.SECONDS, lifeTime = 1)
    public double sin(int value, int timeMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeMs);
        } catch (InterruptedException ex) {
        }
        return Math.sin(value);
    }

}
