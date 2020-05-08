package think.container;

import java.util.Objects;

/**
 * @Auther lidaxia
 * @Date 2020-05-09 7:31
 */
public class SetType {

    int i;

    public SetType(int i) {
        this.i = i;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof SetType && (i == ((SetType) o).i);
    }


    @Override
    public String toString() {
        return Integer.toString(i);
    }
}
