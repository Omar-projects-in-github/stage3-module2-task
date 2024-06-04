package com.mjc.school.service;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class NewsMapper {

    @Autowired
    protected BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService;

    public abstract List<NewsDtoResponse> modelListToDtoList(List<News> modelList);

    @Mapping(
            target = "authorDto",
            expression =
                    "java(model.getAuthorId() != null ? authorService.readById(model.getAuthorId()) : null)")
    public abstract NewsDtoResponse modelToDto(News model);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdatedDate", ignore = true),
    })
    public abstract News dtoToModel(NewsDtoRequest dto);
}
