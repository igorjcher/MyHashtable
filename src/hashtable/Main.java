package hashtable;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> map;
        map = new HashMap<>();
        //map = new Hashtable2<>();
        test2(map);
        //Hashtable h = new HashtableImpl();
        //test3(h);
    }
     //--------------------------------------------------------------------->>>>//tommorow 0xff
    
    static void test1() {
        String dog = "Собачка собакен";
        String dog2 = "Собачка собакен";
        Double a = 0.12931;
        Double b = 0.12931;
        
        System.out.println("dog  " + a.hashCode());
        System.out.println("dog2 " + b.hashCode());
        
        HashtableImpl h = new HashtableImpl();
        h.put("d1", dog);
        System.out.println("size in the beginning " + h.size());        
        h.put("d2", dog2);
        h.put("d1", "Spike");
        h.put("d3", "Red");
        h.put("d4", "Blue");
        h.put("d5", "Black");
        h.put("d6", "White");
        h.put("d7", "Green");
        h.put("d8", "Yellow");
        h.put("d9", "Grey");
        h.put("d10", "Purle");
        String value = h.remove("d1");
        System.out.println("value " + value);
        System.out.println("Собачка собакен: " + h.get("Собачка собакен"));
        System.out.println("size in the end " + h.size());
        
        //h.getPairs().stream().forEach(System.out::println);
        
        for (HashtableImpl.Pair pair : h.getPairs()) {
            System.out.println(pair);
        }
    }
    
    static void test2(Map<String, String> map) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }
        long stop = System.currentTimeMillis();
        System.out.println("test2() " + (stop - start) + " ms");
    }
    
    static void test3(Hashtable h) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            h.put(String.valueOf(i), String.valueOf(i));
        }
        long stop = System.currentTimeMillis();
        System.out.println("test2() " + (stop - start) + " ms");
    }
}
