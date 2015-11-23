package br.com.logique.methodcache;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Gustavo on 21/11/2015.
 */
public class BasicEternalCacheTest {

    private BeanEternalCached enhanceTest;
    private BeanEternalCached realImplementation;

    @Before
    public void setUp() {
        realImplementation = new BeanEternalCached();
        enhanceTest = (BeanEternalCached) CacheEnhancer.newInstance(realImplementation);
        CacheEnhancer.invalidAllCache();
    }

    @Test
    public void sameResult() {
        double result = enhanceTest.sin(10, 0);
        double resultReal = realImplementation.sin(10, 0);
        assertEquals(resultReal, result, 0.001);
    }

    @Test
    public void cacheTime(){
        int executionTime = 250;
        long time1 = callAndGetTime(10, executionTime);
        long time2 = callAndGetTime(10, executionTime);
        Assert.assertTrue((time1) >= executionTime);
        Assert.assertTrue((time2) < executionTime);
    }

    private long callAndGetTime(int param1, int executionTime){
        long t1 = System.currentTimeMillis();
        enhanceTest.sin(param1, executionTime);
        long t2 = System.currentTimeMillis();
        return t2-t1;
    }

}
