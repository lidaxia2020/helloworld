package gc;

/**
 * @Auther lidaxia
 * @Date 2020-06-02 14:56
 */
public class ReferenceCountingGc {

    private Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
//        ReferenceCountingGc obj1 = new ReferenceCountingGc();
//        ReferenceCountingGc obj2 = new ReferenceCountingGc();
//
//        obj1.instance = obj2;
//        obj2.instance = obj1;
//
//        obj1 = null;
//        obj2 = null;
//
//        System.gc();
        testAllocation();
    }

    /**
     * vm 参数： -verbose:gc -Xms20m -Xmn20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    public static void testAllocation(){
        byte[] a1,a2,a3,a4;
        a1 = new byte[2 * _1MB];
        a2 = new byte[2 * _1MB];
        a3 = new byte[2 * _1MB];
        a4 = new byte[4 * _1MB];
    }

}
