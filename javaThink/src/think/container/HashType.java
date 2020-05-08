package think.container;

/**
 * @Auther lidaxia
 * @Date 2020-05-09 7:33
 */
public class HashType extends SetType{
    public HashType(int i) {
        super(i);
    }

    @Override
    public int hashCode() {
        return i;
    }
}
