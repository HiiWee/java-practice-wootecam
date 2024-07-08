package next.reflection;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

public class Junit3Runner {

    @Test
    public void runner() throws Exception {
        Class clazz = Junit3Test.class;
        Method[] methods = clazz.getDeclaredMethods();
        Object instance = clazz.getDeclaredConstructor().newInstance();

        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                method.setAccessible(true); // private 접근제어자를 가진 메소드를 실행할 수 있는 권한을 준다.
                method.invoke(instance);
            }
        }
    }
}
