package hashtable;

public class ArrayHashtable {
    private int size = 10;
    private Pair[] pairs = new Pair[size];
    
    void put(String key, String value) {
        if (size + 1 >= pairs.length) {
            ensureCapacity();
        }
        
        for (int i = 0; i < pairs.length; i++) {
            if (pairs[i] == null) {
                pairs[i] = new Pair(key, value);
            }
        }
    }
    
    public String get(String key) {
        return null;
    }
    
    private void ensureCapacity() {
        Pair[] newPairs = new Pair[pairs.length * 2];
        for (int i = 0; i < pairs.length; i++) {
            newPairs[i] = pairs[i];
        }
        pairs = newPairs;
    }
    
    static class Pair {
        String key;
        String value;

        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }
       
    }
}
