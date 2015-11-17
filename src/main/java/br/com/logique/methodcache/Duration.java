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

import java.util.concurrent.TimeUnit;

/**
 * Store a time duration with amount and time unit.
 *
 * @author Gustavo Leitão
 */
public class Duration {

    private final int lifeTime;
    private final TimeUnit timeUnit;

    public Duration(int lifeTime, TimeUnit unit) {
        this.lifeTime = lifeTime;
        this.timeUnit = unit;
    }

    public static Duration withLifetime(int lifetime, TimeUnit timeUnit) {
        return new Duration(lifetime, timeUnit);
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

}
