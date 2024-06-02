package com.mjc.school.service.aspects;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.validator.Validator;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidatingAspect {
    private final Validator validator;

    @Autowired
    public ValidatingAspect(Validator validator) {
        this.validator = validator;
    }

    @Before("@annotation(com.mjc.school.service.annotations.ValidatingNews) && args(newsDtoRequest)")
    public void validateNewsDtoRequest(NewsDtoRequest newsDtoRequest) {
        validator.validateNewsDtoRequest(newsDtoRequest);
    }

    @Before("@annotation(com.mjc.school.service.annotations.ValidatingAuthor) && args(authorDtoRequest)")
    public void validateAuthorDtoRequest(AuthorDtoRequest authorDtoRequest) {
        validator.validateAuthorDtoRequest(authorDtoRequest);
    }

    @Before("@annotation(com.mjc.school.service.annotations.ValidatingNewsId) && args(id)")
    public void validateNewsDtoId(Long id) {
        validator.validateNewsId(id);
    }

    @Before("@annotation(com.mjc.school.service.annotations.ValidatingAuthorId) && args(id)")
    public void validateAuthorDtoId(Long id) {
        validator.validateAuthorId(id);
    }
}
