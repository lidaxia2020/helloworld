package main.java.com.helloworld.zhujie;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author daxia li
 * @time 2019/8/4
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SetProperty {

    String name();

    int leng();
}
