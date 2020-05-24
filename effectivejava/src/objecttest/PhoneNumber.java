package objecttest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Auther lidaxia
 * @Date 2020-05-23 17:04
 */
public final class PhoneNumber {

    private final short areaCode;

    private final short prefix;

    private final short lineNumber;

    public PhoneNumber(short areaCode, short prefix, short lineNumber) {
        rangeCheck(areaCode, 999, "areaCode");
        rangeCheck(prefix, 999, "prefix");
        rangeCheck(lineNumber, 9999, "line number");
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNumber = lineNumber;
    }


    private static void rangeCheck(int arg, int max, String name){
        if (arg < 0 || arg > max){
            throw new IllegalArgumentException(name + ":" + arg);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (!(obj instanceof PhoneNumber)){
            return false;
        }
        PhoneNumber pn = (PhoneNumber) obj;
        return pn.lineNumber == lineNumber
                && pn.prefix == prefix
                && pn.areaCode == areaCode;
    }

    public static void main(String[] args) {
        Map<PhoneNumber, String> map = new HashMap<>();
        map.put(new PhoneNumber((short)707, (short)876, (short)888), "jenny");
        map.forEach((k,v)->{
            System.out.println(k);
            System.out.println(v);
        });
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "areaCode=" + areaCode +
                ", prefix=" + prefix +
                ", lineNumber=" + lineNumber +
                '}';
    }
}
