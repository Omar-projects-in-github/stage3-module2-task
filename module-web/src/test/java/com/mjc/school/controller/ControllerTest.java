package com.mjc.school.controller;

import static com.mjc.school.controller.util.Utils.getAnnotations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static com.mjc.school.controller.util.Constant.COMMAND_HANDLER_ANNOTATION_NAME;
import static com.mjc.school.controller.util.Utils.getControllerImplClasses;

@SpringJUnitConfig(classes = ControllerTest.TestConfig.class)
public class ControllerTest {
    private static final String BASE_CONTROLLER = "com.mjc.school.controller.BaseController";
    private static final String BASE_SERVICE = "com.mjc.school.service.BaseService";

    @Autowired
    private GenericApplicationContext ctx;

    @Test
    void contextShouldHave2Controllers() throws ClassNotFoundException {
        var controllers = ctx.getBeansOfType(Class.forName(BASE_CONTROLLER));
        assertEquals(
                2, controllers.size(), "There should be 2 controllers in context: Author and News.");
    }

    @Test
    void controllersShouldDependOnServices() throws ClassNotFoundException {
        var controllers = ctx.getBeansOfType(Class.forName(BASE_CONTROLLER));
        var services = ctx.getBeansOfType(Class.forName(BASE_SERVICE));

        Set<String> controllersDependencies =
                controllers.keySet().stream()
                        .flatMap(key -> Arrays.stream(ctx.getBeanFactory().getDependenciesForBean(key)))
                        .collect(Collectors.toSet());

        assertTrue(
                controllersDependencies.containsAll(services.keySet()),
                "'Controllers' should delegate request processing to 'Services'.");
    }

    @Test
    void methodsInControllerImplClassesShouldHaveCommandHandlerAnnotation()
            throws ClassNotFoundException {
        Class commandHandlerAnnotation =
                getAnnotations().stream()
                        .filter(an -> an.getName().contains(COMMAND_HANDLER_ANNOTATION_NAME))
                        .findFirst()
                        .get();
        assertNotNull(
                commandHandlerAnnotation,
                String.format(
                        "%s annotation should be defined in module-web.", COMMAND_HANDLER_ANNOTATION_NAME));

        for (Class controllerImpl : getControllerImplClasses()) {
            assertTrue(
                    Arrays.stream(controllerImpl.getDeclaredMethods())
                            .filter(m -> !m.isBridge())
                            .anyMatch(m -> m.isAnnotationPresent(commandHandlerAnnotation)),
                    String.format(
                            "%s class should have methods with %s annotation.",
                            controllerImpl.getName(), COMMAND_HANDLER_ANNOTATION_NAME));
        }
    }

    @Configuration
    @ComponentScan(basePackages = "com.mjc.school")
    public static class TestConfig {}
}
