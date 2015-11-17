/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logique.methodcache.util;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Wrap two class in just one
 *
 * @param <F> type os the first class
 * @param <S> type of the second class
 * @author Gustavo Leitão
 */
public class Pair<F, S> implements Comparable<Pair<F, S>>, Serializable, Remote {

    private final F first;
    private final S second;

    private Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<F, S>(first, second);
    }

    @Override
    public int compareTo(Pair<F, S> o) {
        int cmp = compare(first, o.first);
        return cmp == 0 ? compare(second, o.second) : cmp;
    }

    private static int compare(Object o1, Object o2) {
        return o1 == null ? o2 == null ? 0 : -1 : o2 == null ? +1 : ((Comparable) o1).compareTo(o2);
    }

    @Override
    public int hashCode() {
        return 31 * hashcode(first) + hashcode(second);
    }

    private static int hashcode(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return equal(first, ((Pair) obj).first) && equal(second, ((Pair) obj).second);
    }

    private boolean equal(Object o1, Object o2) {
        return o1 == null ? o2 == null : (o1 == o2 || o1.equals(o2));
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ')';
    }
}
