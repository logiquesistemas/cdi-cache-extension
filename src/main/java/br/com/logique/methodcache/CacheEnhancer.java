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
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * A enhancer to intercepts method calls.
 *
 * @author Gustavo Leitão
 */
public class CacheEnhancer implements InvocationHandler {

    private final Object proxyableObject;
    private static final Logger logger = LoggerFactory.getLogger(CacheEnhancer.class);
    private static final SupplierCache CACHE = SupplierLocateService.getSupplierCache();

    private CacheEnhancer(Object obj) {
        this.proxyableObject = obj;
    }

    public static Object newInstance(Object obj) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(new CacheEnhancer(obj));
        return enhancer.create();
    }

    public static void invalidAllCache(){
        CACHE.clearAll();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodParameter methodParameter = MethodParameter.of(proxyableObject.getClass(), method, args);
        logger.trace("invoking a method {} of {} proxy.", method.getName(), proxy);
        Object retorno;

        Supplier<Object> supplier = CACHE.get(methodParameter);

        if (supplier == null){
            supplier = SupplierFactory.getSupplier(proxyableObject, method, args);
            retorno = supplier.get();
            CACHE.put(methodParameter, supplier);
        }else{
            retorno = supplier.get();
        }

        return retorno;
    }

}
