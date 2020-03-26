package hashtable;

public interface Hashtable {
    String get(String key);
    void put(String key, String value);
    String remove(String key);
    int size();
}
