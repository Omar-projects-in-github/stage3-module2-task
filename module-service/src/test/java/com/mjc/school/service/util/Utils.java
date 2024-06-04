package com.mjc.school.service.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Utils {

    private static List<Class> classes = null;

    public static List<Class> getClasses() {
        if (classes == null) {
            classes = getProjectClasses();
        }
        return classes;
    }

    private static List<Class> getProjectClasses() {
        classes = new ArrayList<>();
        String jarFilePath =
                "jar:file:".concat(Constant.BUILD_PATH).concat(getProjectName().concat(Constant.JAR_EXTENSION).concat("!/"));
        try (URLClassLoader cl = URLClassLoader.newInstance(new URL[] {new URL(jarFilePath)})) {
            for (String name : getJarClassesName()) {
                classes.add(cl.loadClass(name));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return classes;
    }

    private static String getProjectName() {
        String userDir = System.getProperty(Constant.USER_DIRECTORY);
        Path path = Paths.get(userDir);
        return path.getFileName().toString();
    }

    private static List<String> getJarClassesName() {
        File jarFilePath = new File(Constant.BUILD_PATH.concat(getProjectName().concat(Constant.JAR_EXTENSION)));
        List<String> classNames = new ArrayList<>();
        try (JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(Constant.JAVA_CLASSES_EXTENSION)) {
                    String className =
                            jarEntry.getName().replace("/", ".").replace(Constant.JAVA_CLASSES_EXTENSION, "");
                    classNames.add(className);
                }
            }
            return classNames;
        } catch (Exception ex) {
            System.out.println("Can not run test suites. Project build is missing");
            ex.printStackTrace();
        }
        return classNames;
    }
}
