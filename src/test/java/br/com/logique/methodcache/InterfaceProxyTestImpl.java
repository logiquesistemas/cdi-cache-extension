/*
 * Copyright (C) 2015 Logique Sistemas
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

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of InterfaceProxyTest to test the cache proxy.
 *
 * @author Gustavo Leitão
 */
public class InterfaceProxyTestImpl implements InterfaceProxyTest {

    private Logger logger = LoggerFactory.getLogger(InterfaceProxyTestImpl.class);

    @Cacheable(lifeTime = 30, unit = TimeUnit.SECONDS)
    @Override
    public int doSomething(int arg) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
        }
        return arg * 2;
    }

}
