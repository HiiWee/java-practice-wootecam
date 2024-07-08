package next.reflection;


import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElapsedTimeTest {

    private static final Logger log = LoggerFactory.getLogger(ElapsedTimeTest.class);

    static class TestClass {

        public TestClass() {
        }

        @ElapsedTime
        public void test1() {
            for (int i = 0; i < 1000; i++) {
                System.out.print("");
            }
            System.out.println("test1 실행");
        }

        public void test2() {
            System.out.println("test2 실행");
        }
    }

    @Test
    void elapsedTimeCheck() throws Exception {
        Class<TestClass> testClassClass = TestClass.class;
        TestClass testClass = testClassClass.getConstructor().newInstance();

        Method[] methods = testClassClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ElapsedTime.class)) {
                method.setAccessible(true);
                long currentTimeMillis = System.currentTimeMillis();
                method.invoke(testClass);
                long elapsedTimeMillis = System.currentTimeMillis() - currentTimeMillis;

                log.info("{}ms", elapsedTimeMillis);
            }
        }
    }
}
