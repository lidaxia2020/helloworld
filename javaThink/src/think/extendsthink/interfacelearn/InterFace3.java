package think.extendsthink.interfacelearn;

/**
 * @Auther lidaxia
 * @Date 2020-04-25 15:42
 */
public class InterFace3 implements InterFace1,InterFace2{
    @Override
    public void test() {
        System.out.println("###############");
    }

    public static void main(String[] args) {
        InterFace3 interFace3 = new InterFace3();
        interFace3.test();
    }
}
