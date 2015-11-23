package br.com.logique.methodcache;
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
import com.google.common.base.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Method supplier.
 *
 * Created by Gustavo Leitão on 21/11/2015.
 */
public class MethodSupplier implements Supplier<Object>{

    private final Object proxy;
    private final Method method;
    private final Object[] args;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodSupplier.class);

    private MethodSupplier(Object proxy, Method method, Object[] args) {
        this.proxy = proxy;
        this.method = method;
        this.args = args;
    }

    public static MethodSupplier of(Object proxy, Method method, Object[] args){
        return new MethodSupplier(proxy, method, args);
    }

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
}
