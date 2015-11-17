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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * Save the method and all parameters.
 *
 * @author Gustavo Leitão
 */
public class MethodParamter {

    private final Method method;
    private final Object[] paramters;

    private MethodParamter(Method method, Object[] paramters) {
        this.method = method;
        this.paramters = paramters;
    }

    public static MethodParamter of(Method method, Object[] paramtes) {
        return new MethodParamter(method, paramtes);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.method);
        hash = 97 * hash + Arrays.deepHashCode(this.paramters);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MethodParamter other = (MethodParamter) obj;
        if (!Objects.equals(this.method, other.method)) {
            return false;
        }
        if (!Arrays.deepEquals(this.paramters, other.paramters)) {
            return false;
        }
        return true;
    }

}
