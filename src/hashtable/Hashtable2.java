package hashtable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Hashtable2<K, V> implements Map<K, V> {
    
    private static double LOAD_FACTOR = 0.75;

    private List<Pair<K, V>>[] table = (List<Pair<K, V>>[]) new List[8];
    private int size = 0;

    @Override
    public V get(Object key) {
        if (key == null) {
            return null;
        }
        if (size == 0) {
            return null;
        }

        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % table.length);

        List<Pair<K, V>> bucket = table[bucketIndex];

        if (bucket == null) {
            return null;
        }

        for (int i = 0; i < bucket.size(); i++) {
            Pair<K, V> pair = bucket.get(i);
            if (pair.key.equals(key)) { //0xff
                return pair.value;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (isLimitRunOut()) {
            rebuildTable();
        }

        if (put(table, key, value)) {
            size++;
        }
        return value;
    }

    private static <K, V> boolean put(List<Pair<K, V>>[] table, K key, V value) {
        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % table.length);
        List<Pair<K, V>> bucket = table[bucketIndex]; //bucket - список ключ/значение
        if (bucket == null) {
            bucket = new ArrayList<>();
            bucket.add(new Pair(key, value));
            table[bucketIndex] = bucket;
            return true;
        } else {
            for (int i = 0; i < bucket.size(); i++) {
                Pair<K, V> currentPair = bucket.get(i);
                if (currentPair.key.equals(key)) {
                    currentPair.value = value;
                    return false;
                }
            }
            bucket.add(new Pair<>(key, value));
            return true;
        }
    }

    private boolean isLimitRunOut() {
        return (((double) size) / table.length) >= LOAD_FACTOR;
    }

    private void rebuildTable() {
        List<Pair<K, V>>[] newTable = (List<Pair<K, V>>[]) new List[table.length * 2];

        List<Pair<K, V>> pairs = getPairs();

        int size = 0;
        for (int i = 0; i < pairs.size(); i++) {
            Pair<K, V> pair = pairs.get(i);
            if (put(newTable, pair.key, pair.value)) {
                size++;
            }
        }

        this.table = newTable;
        this.size = size;
    }

    public List<Pair<K, V>> getPairs() {
        List<Pair<K, V>> pairs = new ArrayList<>();

        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }

            List<Pair<K, V>> bucket = table[i];
            for (int j = 0; j < bucket.size(); j++) {
                pairs.add(new Pair(bucket.get(j)));
            }
        }

        return pairs;
    }

    @Override
    public V remove(Object key) {
        if (key == null) {
            return null;
        }
        if (size == 0) {
            return null;
        }

        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % table.length);
        List<Pair<K, V>> bucket = table[bucketIndex];
        if (bucket == null) {
            return null;
        }

        for (int i = 0; i < bucket.size(); i++) {
            Pair<K, V> pair = bucket.get(i);
            if (pair.key.equals(key)) { //0xff
                bucket.remove(pair);
                size--;
                return pair.getValue();
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class Pair<K, V> implements Entry<K, V>{
        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Pair(Pair<K, V> that) {
            this.key = that.key;
            this.value = that.value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            return "{key: " + key + ", value: " + value + "}";
        }
    }
}
