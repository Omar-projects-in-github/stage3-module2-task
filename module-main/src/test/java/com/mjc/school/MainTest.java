package com.mjc.school;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static com.mjc.school.util.Utils.getClasses;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MainTest {

    @Test
    public void doNotInjectControllerInModuleMain() throws ClassNotFoundException {
        boolean hasControllerField = false;
        for (Class c : getClasses()) {
            hasControllerField =
                    Arrays.stream(c.getDeclaredFields())
                            .map(Field::getType)
                            .anyMatch(
                                    fieldClass ->
                                            fieldClass.getName().contains("BaseController")
                                                    || Arrays.stream(fieldClass.getInterfaces())
                                                    .anyMatch(i -> i.getName().contains("BaseController")));
            if (hasControllerField) {
                break;
            }
        }
        assertFalse(
                hasControllerField,
                "Do not inject Controller in module-main. Use custom annotations to organize communication");
    }
}
