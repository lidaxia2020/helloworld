package think.classinfo;

import java.util.Arrays;

/**
 * @Auther lidaxia
 * @Date 2020-05-02 9:00
 */
public class FancyToy extends Toy implements HasBatteries,Shoots,Waterproof{

    static void printInfo(Class cc){
        System.out.println("c Name = " + cc.getName());
        System.out.println("is interface = " + cc.getInterfaces());
        System.out.println("Simple Name = " + cc.getSimpleName());
        System.out.println("canonical Name = " + cc.getCanonicalName());
    }

    public static void main(String[] args) {
        Class c = null;
        try {
            // 返回与给定字符串名称的类或接口相关联的 类对象。
            c = Class.forName("think.classinfo.FancyToy");
        }catch (ClassNotFoundException e) {
            e.getStackTrace();
            System.exit(1);
        }

        System.out.println("c = " + c);

        //确定由该对象表示的类或接口实现的接口
        for (Class face : c.getInterfaces()) {
            System.out.println("face = " + face);
        }

        // 返回 类表示此所表示的实体（类，接口，基本类型或void）的超类 类
        Class up = c.getSuperclass();
        Object object = null;
        try {
            // 创建由此 类对象表示的类的新实例
            object = up.newInstance();
        }catch (InstantiationException e){
            e.printStackTrace();
            System.exit(1);
        }catch (IllegalAccessException e){
            e.printStackTrace();
            System.exit(1);
        }

        printInfo(object.getClass());

    }

}
