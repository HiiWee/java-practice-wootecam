package next.reflection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
    private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("테스트1: 리플렉션을 이용해서 클래스와 메소드의 정보를 정확하게 출력해야 한다.")
    public void showClass() {
        SoftAssertions s = new SoftAssertions();
        Class<Question> clazz = Question.class;
        log.debug("Classs Name {}", clazz.getName());

        // field name
        // getDeclaredFields = 필드 접근제어자 + 패키지 + 타입 + 이름
        System.out.println("1. Class.getDeclaredFields() 출력");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            log.info("declaredField = {}", declaredField);
        }

        System.out.println(
                "============================================================================================================================\n");

        // Field.getName 이름만
        System.out.println("2. Field관련 정보 출력");
        Field[] fields = clazz.getDeclaredFields();

        Arrays.stream(fields)
                .map(Field::getName)
                .forEach(name -> log.info("FieldName = {}", name));

        System.out.println();

        // java.lang.reflect.Modifiers 클래스에 정의된 16진수 상수값을 OR연산하여 반환하는 값
        Arrays.stream(fields)
                .map(Field::getModifiers)
                .forEach(mod -> log.info("FieldModifiers = {}", mod));

        // constructor name
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        System.out.println(
                "============================================================================================================================\n");

        System.out.println("3. 생성자 관련 정보 출력");
        Arrays.stream(constructors)
                .map(Constructor::getName)
                .forEach(name -> log.info("ConstructorName = {}", name));

        Arrays.stream(constructors)
                .map(Constructor::getModifiers)
                .forEach(mod -> log.info("ConstructorModifiers = {}", mod));

        Arrays.stream(constructors)
                .map(Constructor::getParameterTypes)
                .forEach(param -> Arrays.stream(param).forEach(p -> log.info("ParameterType = {}", p)));

        System.out.println(
                "============================================================================================================================\n");

        // method
        System.out.println("4. 메소드 관련 정보 출력");

        Method[] methods = clazz.getDeclaredMethods();
        Arrays.stream(methods)
                .map(Method::getName)
                .forEach(name -> log.info("MethodName = {}", name));

        Arrays.stream(methods)
                .map(Method::getModifiers)
                .forEach(mod -> log.info("MethodModifiers = {}", mod));

        Arrays.stream(methods)
                .map(Method::getParameterTypes)
                .forEach(param -> Arrays.stream(param).forEach(p -> log.info("ParameterType = {}", p)));

    }

    @Test
    public void constructor() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            log.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                log.debug("param type : {}", paramType);
            }
        }
    }

    @Test
    void privateFieldAccess() throws Exception {
        // given
        Class<Student> studentClass = Student.class;
        Student student = studentClass.getConstructor().newInstance();
        log.debug(studentClass.getName());

        // when
        Field name = studentClass.getDeclaredField("name");
        Field age = studentClass.getDeclaredField("age");
        name.setAccessible(true);
        name.set(student, "이름");
        age.setAccessible(true);
        age.setInt(student, 10);

        // then
        assertAll(
                () -> assertThat(student.getName()).isEqualTo("이름"),
                () -> assertThat(student.getAge()).isEqualTo(10)
        );
    }
}
