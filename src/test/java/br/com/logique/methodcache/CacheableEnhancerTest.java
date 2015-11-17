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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Gustavo Leitão
 */
public class CacheableEnhancerTest {

    private InterfaceProxyTestImpl enhanceTest;
    private InterfaceProxyTestImpl realImplementation;

    @Before
    public void setUp() {
        realImplementation = new InterfaceProxyTestImpl();
        enhanceTest = (InterfaceProxyTestImpl) CacheableEnhancer.newInstance(realImplementation);
    }

    @Test
    public void testNewInstance() {
        int result = enhanceTest.multiplier(10, 2);
        assertEquals(20, result);
    }

}
