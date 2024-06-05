package com.mjc.school;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import static com.mjc.school.util.Constant.*;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideOutsideOfPackages;

public class CommonTest {

    private static JavaClasses javaClasses;

    private static DescribedPredicate<? super JavaClass> moduleMain;
    private static DescribedPredicate<? super JavaClass> moduleWeb;
    private static DescribedPredicate<? super JavaClass> moduleService;
    private static DescribedPredicate<? super JavaClass> moduleRepository;

    @BeforeAll
    static void setUp() {
        javaClasses = new ClassFileImporter().importPackages("com.mjc.school");

        moduleMain =
                resideInAPackage(ROOT_PACKAGE)
                        .and(resideOutsideOfPackages(CONTROLLER_PACKAGE, SERVICE_PACKAGE, REPOSITORY_PACKAGE));
        moduleWeb = resideInAPackage(CONTROLLER_PACKAGE);
        moduleService = resideInAPackage(SERVICE_PACKAGE);
        moduleRepository = resideInAPackage(REPOSITORY_PACKAGE);
    }

    @Test
    void modulesShouldFollowLayeredArchitecture() {
        var layeredArchitecture =
                layeredArchitecture()
                        .layer("main")
                        .definedBy(moduleMain)
                        .layer("web")
                        .definedBy(moduleWeb)
                        .layer("service")
                        .definedBy(moduleService)
                        .layer("repository")
                        .definedBy(moduleRepository)
                        .whereLayer("main")
                        .mayNotBeAccessedByAnyLayer()
                        .whereLayer("web")
                        .mayOnlyBeAccessedByLayers("main", "web")
                        .whereLayer("service")
                        .mayOnlyBeAccessedByLayers("web", "service")
                        .whereLayer("repository")
                        .mayOnlyBeAccessedByLayers("service", "repository");

        layeredArchitecture.check(javaClasses);
    }
}
