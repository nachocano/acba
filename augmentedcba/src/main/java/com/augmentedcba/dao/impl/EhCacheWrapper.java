package com.augmentedcba.dao.impl;

import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.Validate;

public class EhCacheWrapper<T extends List<R>, R> {

    private final Ehcache ehCache;
    private final String cacheKey;

    public EhCacheWrapper(final Ehcache ehCache, final String cacheKey) {
        Validate.notNull(ehCache);
        Validate.notEmpty(cacheKey);
        this.ehCache = ehCache;
        this.cacheKey = cacheKey;
    }

    @SuppressWarnings("unchecked")
    public T getAll() {
        final Element element = ehCache.get(cacheKey);
        if (element != null) {
            return (T) element.getObjectValue();
        }
        return null;
    }

    public void addAll(final T values) {
        final Element element = new Element(cacheKey, values);
        ehCache.put(element);
    }

    public void deleteAll() {
        final T all = getAll();
        if (all != null) {
            all.clear();
        }
        ehCache.removeAll();
    }
}
