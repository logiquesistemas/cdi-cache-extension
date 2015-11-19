/*
 * Copyright (C) 2015 Gustavo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.logique.methodcache;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author Gustavo Leitão
 */
public class BasicCacheTest {

    private BeanCached enhanceTest;
    private BeanCached realImplementation;

    @Before
    public void setUp() {
        realImplementation = new BeanCached();
        enhanceTest = (BeanCached) CacheableEnhancer.newInstance(realImplementation);
        CacheableEnhancer.invalidAllCache();
    }

    @Test
    public void sameResult() {
        double result = enhanceTest.pow2(10, 0);
        double resultReal = realImplementation.pow2(10, 0);
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

    @Test
    public void diferentParameters(){
        int executionTime = 250;
        long time1 = callAndGetTime(10, executionTime);
        long time2 = callAndGetTime(8, executionTime);
        Assert.assertTrue((time1) >= executionTime);
        Assert.assertTrue((time2) >= executionTime);
    }

    @Test
    public void timeExpiration() throws InterruptedException {
        int executionTime = 250;
        long time1 = callAndGetTime(10, executionTime);
        TimeUnit.MILLISECONDS.sleep(2500);
        long time2 = callAndGetTime(10, executionTime);
        Assert.assertTrue((time1) >= executionTime);
        Assert.assertTrue((time2) >= executionTime);
    }

    private long callAndGetTime(int param1, int executionTime){
        long t1 = System.currentTimeMillis();
        enhanceTest.pow2(param1, executionTime);
        long t2 = System.currentTimeMillis();
        return t2-t1;
    }

}
