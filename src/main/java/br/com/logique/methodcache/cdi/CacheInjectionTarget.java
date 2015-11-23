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

import br.com.logique.methodcache.CacheEnhancer;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import java.util.Set;

/**
 * Override injection target to produce wrapped instances.
 *
 * Created by Gustavo Leitão.
 */
public class CacheInjectionTarget<X> implements InjectionTarget<X> {

    private InjectionTarget<X> it;

    public CacheInjectionTarget(InjectionTarget<X> it) {
        this.it = it;
    }

    @Override
    public void inject(X instance, CreationalContext<X> ctx) {
        it.inject(instance, ctx);
    }

    @Override
    public void postConstruct(X instance) {
        it.postConstruct(instance);
    }

    @Override
    public void preDestroy(X instance) {
        it.dispose(instance);
    }

    @Override
    public void dispose(X instance) {
        it.dispose(instance);
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return it.getInjectionPoints();
    }

    @Override
    public X produce(CreationalContext<X> ctx) {
        return (X) CacheEnhancer.newInstance(it.produce(ctx));
    }
}


