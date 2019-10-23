
import java.util.*;
import java.io.*;

/*
 * Class containing a mapping between two Keys.
 *
 * @author Murali Koppula.
 */

public class K2KMap<K,V> extends HashMap<K,V> {
    private HashMap<V,K> reverseMap;

    public K2KMap() {
        super();
        this.reverseMap = new HashMap<V,K>();
    }

    public V get(Object k) {
        @SuppressWarnings("unchecked")
        V v = (V)reverseMap.get(k);
        return super.getOrDefault(k, v);
    }

    public V put(K k1, V k2) {
        V prev = super.put(k1, k2);
        reverseMap.put(k2, k1);

        return prev;
    }

    public V putIfAbsent(K k1, V k2) {
        V prev = super.putIfAbsent(k1, k2);
        if (prev == null)
            reverseMap.putIfAbsent(k2, k1);

        return prev;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        super.putAll(m);

        // for (Map.Entry e : m.entrySet())
        //     reverseMap.put(e.getValue(), e.getKey());

        m.forEach((k,v) -> reverseMap.put(v, k));
    }

    public Collection<V> values() {
        return (Collection<V>)reverseMap.keySet();
    }

    public V remove(Object k1) {
        V prev = super.remove(k1);
        if (prev != null)
            reverseMap.remove(prev);

        return prev;
    }

    public void clear() {
        super.clear();
        reverseMap.clear();
    }
}

