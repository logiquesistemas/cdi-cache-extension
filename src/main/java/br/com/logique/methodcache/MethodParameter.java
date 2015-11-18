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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * Save the method and all parameters.
 *
 * @author Gustavo Leitão
 */
public class MethodParameter {

    private final Class clazz;
    private final Method method;
    private final Object[] parameters;

    private MethodParameter(Class clazz, Method method, Object[] parameters) {
        this.clazz = clazz;
        this.method = method;
        this.parameters = parameters;
    }

    public static MethodParameter of(Class clazz, Method method, Object[] parameters) {
        return new MethodParameter(clazz, method, parameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MethodParameter that = (MethodParameter) o;

        return new EqualsBuilder()
                .append(clazz, that.clazz)
                .append(method, that.method)
                .append(parameters, that.parameters)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(clazz)
                .append(method)
                .append(parameters)
                .toHashCode();
    }
}
