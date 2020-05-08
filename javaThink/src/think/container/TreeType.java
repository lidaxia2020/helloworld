package think.container;

/**
 * @Auther lidaxia
 * @Date 2020-05-09 7:34
 */
public class TreeType extends SetType implements Comparable<TreeType>{


    public TreeType(int i) {
        super(i);
    }

    @Override
    public int compareTo(TreeType o) {
        // 降序
        return (o.i < i ? -1 : (o.i == i ? 0 : 1));
        // 升序
//        return (o.i < i ? 1 : (o.i == i ? 0 : -1));
    }
}
