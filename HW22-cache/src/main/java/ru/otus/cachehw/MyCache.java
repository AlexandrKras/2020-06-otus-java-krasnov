package ru.otus.cachehw;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import static ru.otus.cachehw.MyCache.Actions.*;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> cache = new WeakHashMap();
    private final Set<WeakReference<HwListener<K, V>>> listeners = new HashSet<>();
    private final Set<Enum<Actions>> typeActions = new HashSet<>();

    @Override
    public void put(K key, V value) {
        boolean isNaN = cache.containsKey(key);
        cache.put(key, value);
        if (isNaN) {
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
        listeners.removeIf(hwListener -> hwListener.get().equals(listener));
    }

    public void addAction(Actions action) {
        typeActions.add(action);
    }

    public void removeAction(Actions action) {
        typeActions.remove(action);
    }

    private void doEvent(K key, V value, Actions action) {
        try {
            if (typeActions.contains(action)) {
                for (var hwListener : listeners) {
                    var listener = hwListener.get();
                    if (listener == null) {
                        continue;
                    }
                    listener.notify(key, value, action.name());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public enum Actions {
        CREATE,
        UPDATE,
        REMOVE;
    }
}
