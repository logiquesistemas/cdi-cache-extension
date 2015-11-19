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


import com.google.common.base.Supplier;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface to supplier cache.
 *
 * Created by Gustavo Leitão
 */
@ApplicationScoped
public class SupplierCacheImpl implements SupplierCache {

    private Map<MethodParameter,Supplier<Object>> cache = new ConcurrentHashMap<>();

    @Override
    public void put(MethodParameter key, Supplier<Object> value) {
        cache.put(key,value);
    }

    @Override
    public Supplier<Object> get(MethodParameter key) {
        return cache.get(key);
    }

    @Override
    public boolean containsKey(MethodParameter key) {
        return cache.containsKey(key);
    }

    @Override
    public void clearAll() {
        cache.clear();
    }
}
