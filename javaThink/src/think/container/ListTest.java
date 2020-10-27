package think.container;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther lidaxia
 * @Date 2020-04-28 21:26
 */
public class ListTest {

    public static void main(String[] args) {
        List list = Arrays.asList("1","2","3");
//        list.add("3");
        list.forEach(i->{
            System.out.println("args = " + i);
        });

        for (int i = 0; i < list.size(); i++){
            System.out.println("i = " + list.get(i));
        }


    }
}
