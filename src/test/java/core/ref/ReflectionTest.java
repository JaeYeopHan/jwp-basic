package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (int i = 0; i < constructors.length; i++) {
            Parameter[] params = constructors[i].getParameters();
        }
    }

    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        Field[] fileds = clazz.getDeclaredFields();

        Student student = new Student();
        for (int i = 0; i < fileds.length; i++) {
            logger.debug(fileds[i].getName());
            if (fileds[i].getName().equals("name")) {
                fileds[i].setAccessible(true);
                try {
                    fileds[i].set(student, "javajigi");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.debug("studentName: {}", student.getName());
    }
}
