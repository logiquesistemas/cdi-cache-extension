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
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A proxy to intercept method calls
 *
 * @author Gustavo Leitão
 */
public class CacheableProxy implements InvocationHandler {

    private final Object proxyableObject;
    private final Map<MethodParamter, Supplier<Object>> suplierMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(CacheableEnhancer.class);

    private CacheableProxy(Object obj) {
        this.proxyableObject = obj;
    }

    public static Object newInstance(Object obj) {
        return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new CacheableProxy(obj));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodParamter pair = MethodParamter.of(method, args);
        System.out.println("chamou o proxy...");
        if (!suplierMap.containsKey(pair)) {
            criarSuplier(proxy, method, args);
        }
        Supplier<Object> suplier = suplierMap.get(pair);
        return suplier.get();
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

    private void criarSuplier(Object proxy, Method method, Object[] args) {
        System.out.println("criando proxy: " + method + "-" + args);
        Duration duration = getDuration(method);

        Supplier<Object> suplier = Suppliers.memoizeWithExpiration(new Supplier<Object>() {
            @Override
            public Object get() {
                try {
                    return method.invoke(proxyableObject, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    //TODO logar
                }
                return null;
            }
        }, duration.getLifeTime(), duration.getTimeUnit());
        suplierMap.put(MethodParamter.of(method, args), suplier);
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
