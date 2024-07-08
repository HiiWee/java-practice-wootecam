package next.reflection;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

/**
 * @Testable을 붙이면 IDE에서 테스트 가능한 메소드를 추적해 Run 마크를 달아줌
 * https://junit.org/junit5/docs/5.2.0/api/org/junit/platform/commons/annotation/Testable.html
 */
public class Junit4Runner {
    @Test
    public void run() throws Exception {
        Class clazz = Junit4Test.class;
        Method[] methods = clazz.getDeclaredMethods();
        Object instance = clazz.getDeclaredConstructor().newInstance();

        for (Method method : methods) {
//            if (Objects.nonNull(method.getAnnotation(MyTest.class))) {
            if (method.isAnnotationPresent(MyTest.class)) {
                method.setAccessible(true); // private 접근제어자를 가진 메소드를 실행할 수 있는 권한을 준다.
                method.invoke(instance);
            }
        }
    }
}
