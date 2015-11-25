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


import br.com.logique.methodcache.util.PropertiesUtil;
import com.google.common.base.Supplier;
import com.google.common.cache.CacheBuilder;

/**
 * Interface to supplier cache.
 *
 * Created by Gustavo Leitão
 */
public class SupplierCacheImpl implements SupplierCache {

    private static long LIMIT_CACHE = PropertiesUtil.getLongProperty("cdi-cache.max_size", Long.MAX_VALUE);

    private com.google.common.cache.Cache<MethodParameter, Supplier<Object>> cache = CacheBuilder.newBuilder()
            .maximumSize(LIMIT_CACHE)
            .build();

    @Override
    public void put(MethodParameter key, Supplier<Object> value) {
        cache.put(key,value);
    }

    @Override
    public Supplier<Object> get(MethodParameter key) {
        return cache.getIfPresent(key);
    }

    @Override
    public boolean containsKey(MethodParameter key) {
        return cache.getIfPresent(key) != null;
    }

    @Override
    public void clearAll() {
        cache.invalidateAll();
    }

}
