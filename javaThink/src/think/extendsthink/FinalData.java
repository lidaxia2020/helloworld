package think.extendsthink;

import java.util.Random;

/**
 * @Auther lidaxia
 * @Date 2020-04-24 8:08
 */
public class FinalData {

    private static Random random = new Random(47);

    private String id;

    public FinalData(String id) {
        this.id = id;
    }

    private final int valueOne = 9;
    private static final int VALUE_TWO = 99;

    public static final int VALUE_THREE = 39;

    private final int i4 = random.nextInt(20);
    static final int INT_5 = random.nextInt(20);
    private Value value = new Value(11);
    private final Value value2 = new Value(22);
//    private static final Value value3 = new Value(33);

    private final int[] a = {1,2,3,4,5,6};

    @Override
    public String toString() {
        return "FinalData{" +
                "id='" + id + '\'' +
                ", i4=" + i4 + ", INT_5=" + INT_5 +
                '}';
    }


    public static void main(String[] args) {
        FinalData fd1 = new FinalData("fd1");
        fd1.value2.i++;
    }

    class Value {
        int i;

        public Value(int i) {
            this.i = i;
        }
    }
}
