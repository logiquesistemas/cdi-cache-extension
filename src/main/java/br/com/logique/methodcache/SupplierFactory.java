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
import br.com.logique.methodcache.annotations.EternalCache;
import br.com.logique.methodcache.util.AnnotationUtil;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


/**
 * Method supplier factory.
 *
 * Created by Gustavo Leitão on 19/11/2015.
 */
public class SupplierFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierFactory.class);

    /**
     * Create new timed supplier.
     *
     * @param proxy  Class instance to be cached
     * @param method method to be cached
     * @param args   method args
     * @return new supplier
     */
    public static Supplier<Object> getSupplier(Object proxy, final Method method, final Object[] args) {
        LOGGER.trace("creating CACHE supplier to method {} of proxy {}", method.getName(), proxy);
        Supplier<Object> supplier = getSupplierByType(proxy, method, args);
        return supplier;
    }

    private static Supplier<Object> getSupplierByType(Object proxy, Method method, Object[] args) {
        Supplier<Object> supplier = null;
        Optional<TimedCache> timedAnnotation = AnnotationUtil.getAnnotation(proxy, method, TimedCache.class);

        if (timedAnnotation.isPresent()) {
            supplier = getTimedSupplier(timedAnnotation, proxy, method, args);
        } else {
            Optional<EternalCache> eternalAnnotation = AnnotationUtil.getAnnotation(proxy, method, EternalCache.class);
            if (eternalAnnotation.isPresent()) {
                supplier = getEternalSupplier(proxy, method, args);
            }
        }
        return supplier;
    }

    private static Supplier<Object> getEternalSupplier(final Object proxy, final Method method, final Object[] args) {
        return Suppliers.memoize(MethodSupplier.of(proxy, method, args));
    }

    private static Supplier<Object> getTimedSupplier(Optional<TimedCache> annotation, final Object proxy, final Method method, final Object[] args) {
        Duration duration = getDuration(annotation);
        return Suppliers.memoizeWithExpiration(MethodSupplier.of(proxy, method, args),
                duration.getLifeTime(), duration.getTimeUnit());
    }

    private static Duration getDuration(Optional<TimedCache> annotation) {

        int lifeTime;
        TimeUnit unit;

        if (annotation.isPresent()) {
            lifeTime = annotation.get().lifeTime();
            unit = annotation.get().unit();
        } else {
            lifeTime = 1;
            unit = TimeUnit.MILLISECONDS;
        }

        return Duration.withLifetime(lifeTime, unit);
    }


}
