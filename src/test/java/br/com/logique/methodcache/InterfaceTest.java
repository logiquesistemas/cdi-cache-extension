package br.com.logique.methodcache;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Gustavo on 19/11/2015.
 */
public class InterfaceTest {

    private Interface interfaceCached;
    private Interface interfaceImpl;

    @Before
    public void setUp() {
        interfaceImpl = new InterfaceImpl01();
        interfaceCached = (Interface) CacheableEnhancer.newInstance(interfaceImpl);
        CacheableEnhancer.invalidAllCache();
    }

    @Test
    public void sameResult() {
        double result = interfaceCached.pow2(10, 0);
        double resultReal = interfaceImpl.pow2(10, 0);
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
        interfaceCached.pow2(param1, executionTime);
        long t2 = System.currentTimeMillis();
        return t2-t1;
    }


}
