package com.mjc.school.main;

import com.mjc.school.controller.menu.MenuController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MenuController app = context.getBean(MenuController.class);
        app.runApp();
    }
}
