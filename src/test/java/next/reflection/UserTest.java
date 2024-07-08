package next.reflection;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import next.optional.User;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void userTest() throws Exception {
        // given
        Class<User> userClass = User.class;

        // when
        User user = null;
        Constructor<?>[] constructors = userClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 2 && parameterTypes[0] == String.class
                    && parameterTypes[1] == Integer.class) {
                user = (User) constructor.newInstance("철수", 10);
            }
        }

        // then
        assertThat(user.getName()).isEqualTo("철수");
        assertThat(user.getAge()).isEqualTo(10);
    }
}
