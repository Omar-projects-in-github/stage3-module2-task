package com.mjc.school.controller;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.mjc.school.controller.util.Utils.getControllerImplPackageClasses;

public class AuthorControllerTest {

    @Test
    public void authorControllerClassShouldHavePartOfCreateMethodName() {
        checkClassHaveMethod("create");
    }

    @Test
    public void authorControllerClassShouldHavePartOfUpdateMethodName() {
        checkClassHaveMethod("update");
    }

    @Test
    public void authorControllerClassShouldHavePartOfDeleteMethodName() {
        checkClassHaveMethod("delete");
    }

    @Test
    public void authorControllerClassShouldHavePartOfReadByMethodName() {
        checkClassHaveMethod("readBy");
    }

    @Test
    public void authorControllerClassShouldHavePartOfReadAllMethodName() {
        checkClassHaveMethod("readAll");
    }

    private void checkClassHaveMethod(String method) {
        List<Class> controllerClasses = getControllerImplPackageClasses();
        Class authorController =
                controllerClasses.stream()
                        .filter(c -> c.getName().contains("AuthorController"))
                        .findFirst()
                        .get();
        assertNotNull(
                authorController,
                "AuthorController class should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");

        Method[] methods = authorController.getDeclaredMethods();
        String methodNames =
                Arrays.stream(methods).map(Method::getName).collect(Collectors.toSet()).toString();
        assertTrue(
                methodNames.contains(method),
                String.format("Controller class should have %s part of the name method.", method));
    }
}
