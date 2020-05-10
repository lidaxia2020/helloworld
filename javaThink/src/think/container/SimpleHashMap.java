//package think.container;
//
//import java.util.*;
//
///**
// * @Auther lidaxia
// * @Date 2020-05-10 17:01
// */
//public class SimpleHashMap<K,V> extends AbstractMap<K,V> {
//
//    static final int SIZE = 997;
//
//    LinkedList<Map.Entry<K,V>>[] buckets = new LinkedList[SIZE];
//
//
//
//    @Override
//    public V put(K key, V value){
//        V oldValue = null;
//        int index = Math.abs(key.hashCode()) % SIZE;
//        if (buckets[index] == null) {
//            buckets[index] = new LinkedList<Map.Entry<K, V>>();
//        }
//        LinkedList<Map.Entry<K, V>> bucket = buckets[index];
//        Map.Entry<K,V> pair = new MapEntry<>(key,value);
//        boolean found = false;
//        ListIterator<Map.Entry<K, V>> it =bucket.listIterator();
//        while (it.hasNext()){
//            Map.Entry<K,V> iPair = it.next();
//            if(iPair.getKey().equals(key)){
//                oldValue = iPair.getValue();
//                it.set(iPair);
//                found = true;
//                break;
//            }
//        }
//        if(!found){
//            buckets[index].add(pair);
//        }
//        return oldValue;
//    }
//
//    @Override
//    public V get(Object key) {
//        int index = Math.abs(key.hashCode()) % SIZE;
//        if(buckets[index] == null){
//            return null;
//        }
//        for (Map.Entry<K,V> iPair : buckets[index]){
//            if(iPair.getKey().equals(key)){
//                return iPair.getValue();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Set<Entry<K, V>> entrySet() {
//        Set<Map.Entry<K,V>> set = new HashSet<Map.Entry<K,V>>();
//        for (LinkedList<Map.Entry<K,V>> bucket : buckets){
//            if(bucket == null){
//                continue;
//            }
//
//            for (Map.Entry<K,V> mpair : bucket){
//                set.add(mpair);
//            }
//            return set;
//        }
//
//        return null;
//    }
//
//    public static void main(String[] args) {
//        SimpleHashMap<String,String> m = new SimpleHashMap<>();
//        m.putAll(Countries.capitals(25));
//        System.out.println(m);
//        System.out.println(m.get("ERITREA"));
//        System.out.println(m.entrySet());
//
//    }
//}
