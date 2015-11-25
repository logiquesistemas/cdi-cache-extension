package br.com.logique.methodcache;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Gustavo on 25/11/2015.
 */
public class LimitedCacheTest {

    private MultiplesCacheBean bean;


    @BeforeClass
    public static void before(){
        System.setProperty("cdi-cache.max_size","0");
    }

    @Before
    public void setUp() {
        bean = (MultiplesCacheBean) CacheEnhancer.newInstance(new MultiplesCacheBean());
        CacheEnhancer.invalidAllCache();
    }

    @Test
    public void cacheTime(){
        int executionTime = 250;
        long time1 = callPow2AndGetTime(10, executionTime);
        long time2 = callPow3AndGetTime(10, executionTime);
        long time3 = callPow4AndGetTime(10, executionTime);
        Assert.assertTrue((time1) >= executionTime);
        Assert.assertTrue((time2) >= executionTime);
        Assert.assertTrue((time3) >= executionTime);

        long time12 = callPow2AndGetTime(10, executionTime);
        long time22 = callPow3AndGetTime(10, executionTime);
        long time32 = callPow4AndGetTime(10, executionTime);
        Assert.assertTrue((time12) >= executionTime);
        Assert.assertTrue((time22) >= executionTime);
        Assert.assertTrue((time32) >= executionTime);

    }

    private long callPow2AndGetTime(int param1, int executionTime){
        long t1 = System.currentTimeMillis();
        bean.pow2(param1, executionTime);
        long t2 = System.currentTimeMillis();
        return t2-t1;
    }

    private long callPow3AndGetTime(int param1, int executionTime){
        long t1 = System.currentTimeMillis();
        bean.pow3(param1, executionTime);
        long t2 = System.currentTimeMillis();
        return t2-t1;
    }

    private long callPow4AndGetTime(int param1, int executionTime){
        long t1 = System.currentTimeMillis();
        bean.pow4(param1, executionTime);
        long t2 = System.currentTimeMillis();
        return t2-t1;
    }


}
