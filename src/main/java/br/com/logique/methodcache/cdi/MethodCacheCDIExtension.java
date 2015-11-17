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
package br.com.logique.methodcache.cdi;

import br.com.logique.methodcache.Cacheable;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import java.util.Set;

/**
 * CDI Extension to override the instantiation behavior od chaded classes.
 *
 * Created by Gustavo on 12/11/2015.
 */
public class MethodCacheCDIExtension implements Extension {

    /**
     * Find any method with {@code Cacheable} annotation to override the injection target.
     *
     * @param pit event to observe
     * @param <X> type of event observed
     */
    <X> void processInjectionTarget(@Observes ProcessInjectionTarget<X> pit) {
        AnnotatedType<X> at = pit.getAnnotatedType();
        Set<AnnotatedMethod<? super X>> methods = at.getMethods();
        for (AnnotatedMethod<? super X> method : methods) {
            if (method.isAnnotationPresent(Cacheable.class)) {
                changeInjectionTarget(pit);
                break;
            }
        }
    }

    private <X> void changeInjectionTarget(ProcessInjectionTarget<X> pit) {
        pit.setInjectionTarget(new CacheInjectionTarget(pit.getInjectionTarget()));
    }

}
