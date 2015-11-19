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

/**
 * Implementation of Interface to test the cache proxy.
 *
 * @author Gustavo Leitão
 */
public class BeanCached {

    @Cacheable(lifeTime = 2, unit = TimeUnit.SECONDS)
    public double pow2(int param1, int executionMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(executionMs);
        } catch (InterruptedException ex) {
        }
        return Math.pow(param1, 2);
    }

}
