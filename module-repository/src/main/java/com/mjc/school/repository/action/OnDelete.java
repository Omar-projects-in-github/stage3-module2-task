package com.mjc.school.repository.action;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OnDelete {
    Operation operation();

    String fieldName();
}
