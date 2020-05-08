package think.container;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Auther lidaxia
 * @Date 2020-05-09 7:36
 */
public class TypesForSets {

    static <T>Set<T> fill(Set<T> set, Class<T> type){
        try {
            for (int i=0; i < 10; i++){
                set.add(type.getConstructor(int.class).newInstance(i));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return set;
    }

    static <T> void test(Set<T> set, Class<T> type){
        // 尝试引入重复的对象
        fill(set,type);
        fill(set,type);
        fill(set,type);
        System.out.println("set = " + set + ", type = " + type);
    }

    public static void main(String[] args) {
        test(new HashSet<HashType>(),HashType.class);
        test(new LinkedHashSet<HashType>(),HashType.class);
        test(new TreeSet<TreeType>(),TreeType.class);

        test(new HashSet<SetType>(),SetType.class);
        test(new HashSet<TreeType>(),TreeType.class);
        test(new LinkedHashSet<HashType>(),HashType.class);
        test(new LinkedHashSet<TreeType>(),TreeType.class);

        try {
            test(new TreeSet<TreeType>(),TreeType.class);
        }catch (Exception e){
            e.getStackTrace();
        }

        try {
            test(new TreeSet<HashType>(),HashType.class);
        }catch (Exception e){
            e.getStackTrace();
        }
    }


}
