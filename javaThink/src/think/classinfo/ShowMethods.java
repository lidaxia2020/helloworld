package think.classinfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @Auther lidaxia
 * @Date 2020-05-03 8:04
 */
public class ShowMethods {

    private static String usage = "" +
            "usge:\n" +
            "showMethods qualified.class,name\n" +
            "to show all methods in class or:\n" +
            "to search for methods involving word";

    private static Pattern pattern = Pattern.compile("\\w+\\.");

    public static void main(String[] args) {
        if (args.length < 0){
            System.out.println(usage);
            System.exit(0);
        }

        int lines = 0;
        try {
            Class<?> c = Class.forName(args[0]);
            Method[] methods = c.getMethods();
            Constructor[] constructors = c.getConstructors();
            if (args.length == 1){
                for (Method method : methods){
                    System.out.println(pattern.matcher(method.toString())
                            .replaceAll(""));
                }
                for (Constructor constructor : constructors){
                    System.out.println(pattern.matcher(constructor.toString())
                            .replaceAll(""));
                }
            }else {
                for (Method method : methods){
                    if(method.toString().indexOf(args[1]) != -1){
                        System.out.println(pattern.matcher(method.toString())
                                .replaceAll(""));
                    }
                }

                }
                for (Constructor constructor : constructors){
                    if(constructor.toString().indexOf(args[1]) != -1){
                        System.out.println(pattern.matcher(constructor.toString())
                                .replaceAll(""));
                    }

            }


        }catch (ClassNotFoundException e){
            e.getStackTrace();
        }
    }

}
