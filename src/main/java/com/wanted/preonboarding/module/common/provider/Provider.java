package com.wanted.preonboarding.module.common.provider;

public interface Provider<K, T> {
    T get(K key);

    <R extends T> R get(K key, Class<R> clazz);

}
