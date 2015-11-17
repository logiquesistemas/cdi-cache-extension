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
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * A enhancer to intercepts method calls.
 *
 * @author Gustavo Leitão
 */
public class CacheableEnhancer implements InvocationHandler {

    private final Object proxyableObject;
    private final static Map<MethodParamter, Supplier<Object>> CACHES = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(CacheableEnhancer.class);

    private CacheableEnhancer(Object obj) {
        this.proxyableObject = obj;
    }

    public static Object newInstance(Object obj) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(new CacheableEnhancer(obj));
        return enhancer.create();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodParamter pair = MethodParamter.of(method, args);
        logger.trace("invoking a method {} of {} proxy.", method.getName(), proxy);
        if (!CACHES.containsKey(pair)) {
            createCache(proxy, method, args);
        }
        Supplier<Object> suplier = CACHES.get(pair);
        return suplier.get();
    }

    private void createCache(Object proxy, final Method method, final Object[] args) {
        logger.trace("creating cache suplier to method {} of proxy {}", method.getName(), proxy);
        Duration duration = getDuration(method);
        Supplier<Object> suplier = Suppliers.memoizeWithExpiration(new Supplier<Object>() {
            @Override
            public Object get() {
                try {
                    method.setAccessible(Boolean.TRUE);
                    return method.invoke(proxyableObject, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    logger.error("Failed to invoke method {} of proxy {}", method.getName(), proxy, ex);
                }
                return null;
            }
        }, duration.getLifeTime(), duration.getTimeUnit());

        CACHES.put(MethodParamter.of(method, args), Suppliers.synchronizedSupplier(suplier));
    }

    private Duration getDuration(Method method) {
        Cacheable annotation = getAnnotation(method);
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

    private Cacheable getAnnotation(Method method) {
        Cacheable annotation = null;
        try {
            Method classMethod = proxyableObject.getClass().getMethod(method.getName(), method.getParameterTypes());
            annotation = classMethod.getDeclaredAnnotation(Cacheable.class);
            if (annotation == null) {
                annotation = method.getDeclaredAnnotation(Cacheable.class);
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            logger.error("Failed to get Cacheable Annotation from {}", method.getName(), ex);
        }
        return annotation;
    }
}
