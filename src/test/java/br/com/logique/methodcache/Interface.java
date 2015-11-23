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

import br.com.logique.methodcache.annotations.TimedCache;

import java.util.concurrent.TimeUnit;

/**
 * A interface to test proxy cache.
 *
 * @author Gustavo Leitão
 */
public interface Interface {

    @TimedCache(unit = TimeUnit.SECONDS, lifeTime = 5)
    double pow2(int value, int timeMs);

    @TimedCache(unit = TimeUnit.SECONDS, lifeTime = 5)
    double sin(int value, int timeMs);

}
