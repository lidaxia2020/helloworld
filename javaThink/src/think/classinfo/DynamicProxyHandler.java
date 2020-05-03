package think.classinfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Auther lidaxia
 * @Date 2020-05-04 6:51
 */
public class DynamicProxyHandler implements InvocationHandler {

    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy = " + proxy + ", method = " + method + ", args = " + Arrays.deepToString(args));
        return method.invoke(proxied, args);
    }

}
