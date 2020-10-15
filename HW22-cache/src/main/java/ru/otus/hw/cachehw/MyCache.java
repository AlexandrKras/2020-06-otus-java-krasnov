package ru.otus.hw.cachehw;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

import static ru.otus.hw.cachehw.MyCache.Actions.*;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    WeakHashMap<K, V> cache = new WeakHashMap();
    Set<WeakReference<HwListener<K, V>>> listeners = new HashSet<>();
    Set<Enum<Actions>> typeActions = new HashSet<>();

    @Override
    public void put(K key, V value) {
        V object = cache.get(key);
        cache.put(key, value);
        if (object == null) {
            doEvent(key, value, CREATE);
        } else {
            doEvent(key, value, UPDATE);
        }
    }

    @Override
    public void remove(K key) {
        V object = cache.remove(key);
        doEvent(key, object, REMOVE);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void event(K key, V value, String action) {
        listeners.forEach(listener -> listener.get().notify(key, value, action));
    }

    public void addAction(Actions action) {
        typeActions.add(action);
    }

    public void removeAction(Actions action) {
        typeActions.remove(action);
    }

    private void doEvent(K key, V value, Actions action) {
        if (typeActions.contains(action)) {
            event(key, value, action.name());
        }
    }

    public enum Actions {
        CREATE,
        UPDATE,
        REMOVE;
    }
}
