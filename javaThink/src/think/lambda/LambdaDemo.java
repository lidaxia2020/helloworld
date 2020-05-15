package think.lambda;

/**
 * @Auther lidaxia
 * @Date 2020-05-15 20:26
 */
public class LambdaDemo {

    public static void main(String[] args) {

        Interface1 i1 =(i)-> i* 2;
        // 常见写法
        Interface1 i2 =i-> i* 2;

    }

}


/**
 * 声明函数接口
 */
@FunctionalInterface
interface Interface1 {
    int doubleNum(int i);

    /**
     * jdk 8 默认实现方法
     * @param x
     * @param y
     * @return
     */
    default int add(int x, int y){
        return x + y;
    }
}
