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
        ReferenceCountingGc obj1 = new ReferenceCountingGc();
        ReferenceCountingGc obj2 = new ReferenceCountingGc();

        obj1.instance = obj2;
        obj2.instance = obj1;

        obj1 = null;
        obj2 = null;

        System.gc();
    }

}
