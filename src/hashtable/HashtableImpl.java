package hashtable;

import java.util.ArrayList;
import java.util.List;

public class HashtableImpl implements Hashtable {

    private static double LOAD_FACTOR = 0.72;

    private List<Pair>[] table = (List<Pair>[]) new List[8];
    private int size = 0;

    @Override
    public String get(String key) {
        if (key == null) {
            return null;
        }
        if (size == 0) {
            return null;
        }

        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % table.length);

        List<Pair> bucket = table[bucketIndex];

        if (bucket == null) {
            return null;
        }

        for (int i = 0; i < bucket.size(); i++) {
            Pair pair = bucket.get(i);
            if (pair.key.equals(key)) { //0xff
                return pair.value;
            }
        }
        return null;
    }

    @Override
    public void put(String key, String value) {
        if (isLimitRunOut()) {
            rebuildTable();
        }

        if (put(table, key, value)) {
            size++;
        }
    }

    private static boolean put(List<Pair>[] table, String key, String value) {
        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % table.length);
        List<Pair> bucket = table[bucketIndex]; //bucket - список ключ/значение
        if (bucket == null) {
            bucket = new ArrayList<>();
            bucket.add(new Pair(key, value));
            table[bucketIndex] = bucket;
            return true;
        } else {
            for (int i = 0; i < bucket.size(); i++) {
                Pair currentPair = bucket.get(i);
                if (currentPair.key.equals(key)) {
                    currentPair.value = value;
                    return false;
                }
            }
            bucket.add(new Pair(key, value));
            return true;
        }
    }

    private boolean isLimitRunOut() {
        return (((double) size) / table.length) >= LOAD_FACTOR;
    }

    private void rebuildTable() {
        List<Pair>[] newTable = (List<Pair>[]) new List[table.length * 2];

        List<Pair> pairs = getPairs();

        int size = 0;
        for (int i = 0; i < pairs.size(); i++) {
            Pair pair = pairs.get(i);
            if (put(newTable, pair.key, pair.value)) {
                size++;
            }
        }

        this.table = newTable;
        this.size = size;
    }

    public List<Pair> getPairs() {
        List<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }

            List<Pair> bucket = table[i];
            for (int j = 0; j < bucket.size(); j++) {
                pairs.add(new Pair(bucket.get(j)));
            }
        }

        return pairs;
    }

    @Override
    public String remove(String key) {
        if (key == null) {
            return null;
        }
        if (size == 0) {
            return null;
        }

        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % table.length);
        List<Pair> bucket = table[bucketIndex];
        if (bucket == null) {
            return null;
        }

        for (int i = 0; i < bucket.size(); i++) {
            Pair pair = bucket.get(i);
            if (pair.key.equals(key)) { //0xff
                bucket.remove(pair);
                size--;
                return pair.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    public static class Pair {

        public String key;
        public String value;

        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Pair(Pair that) {
            this.key = that.key;
            this.value = that.value;
        }

        @Override
        public String toString() {
            return "{key: " + key + ", value: " + value + "}";
        }

    }
}

//128%13 = 11
//100%13 = 9
//List<Pair>[] newTable = (List<Pair>[]) new Object[2];
// int[] array = new array[10];
//array[2] = 0;
//newTable[1] = null;
// Object obj;
// obj = new Cat();
// Cat c = (Cat) obj;
// Cat cat = new Object();
// Object obj = new Cat();
// Cat cat = new Animal();
// Cat cat = (Cat) animal;
// Animal obj = new Cat();
/*
class Box {
    Object obj;
    void put(Cat cat) {
        obj = cat;
    }
    Cat get() {
        return (Cat) obj;
    }
}
 */
