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
import com.google.common.base.Suppliers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


/**
 * Supplier Factory.
 *
 * Created by Gustavo Leitão on 19/11/2015.
 */
public class SupplierFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierFactory.class);

    /**
     * Create new timed supplier.
     *
     * @param proxy Class instance to be chached
     * @param method method to be cached
     * @param args method args
     * @return new supplier
     */
    public static Supplier<Object> getSupplier(Object proxy, final Method method, final Object[] args){

        LOGGER.trace("creating CACHE supplier to method {} of proxy {}", method.getName(), proxy);
        Duration duration = getDuration(proxy, method);
        com.google.common.base.Supplier<Object> supplier = Suppliers.memoizeWithExpiration(new com.google.common.base.Supplier<Object>() {
            @Override
            public Object get() {
                try {
                    method.setAccessible(Boolean.TRUE);
                    return method.invoke(proxy, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    LOGGER.error("Failed to invoke method {} of proxy {}", method.getName(), proxy, ex);
                }
                return null;
            }
        },duration.getLifeTime(), duration.getTimeUnit());

        return supplier;
    }

    private static Duration getDuration(Object proxy, Method method) {
        Cacheable annotation = getAnnotation(proxy, method);
        int lifeTime;
        TimeUnit unit;
        if (annotation == null) {
            lifeTime = 1;
            unit = TimeUnit.MILLISECONDS;
        } else {
            lifeTime = annotation.lifeTime();
            unit = annotation.unit();
        }
        return Duration.withLifetime(lifeTime, unit);
    }

    private static Cacheable getAnnotation(Object proxy, Method method) {
        Cacheable annotation = null;

        try {
            Method classMethod = proxy.getClass().getMethod(method.getName(), method.getParameterTypes());
            annotation = classMethod.getDeclaredAnnotation(Cacheable.class);
            if (annotation == null) {
                //annotation = method.getDeclaredAnnotation(Cacheable.class);
                annotation = AnnotationUtils.findAnnotation(classMethod, Cacheable.class);
            }

        } catch (NoSuchMethodException | SecurityException ex) {
            LOGGER.error("Failed to get Cacheable Annotation from {}", method.getName(), ex);
        }
        return annotation;
    }

}
