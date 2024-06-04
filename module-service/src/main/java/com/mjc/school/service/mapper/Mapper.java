package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    NewsDtoResponse newsModelToNewsDto(News news);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    News newsDtoToNewsModel(NewsDtoRequest newsDto);

    List<NewsDtoResponse> newsModelListToNewsDtoList(List<News> newsList);

    AuthorDtoResponse authorModelToAuthorDto(AuthorModel authorModel);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    AuthorModel authorDtoToAuthorModel(AuthorDtoRequest authorDto);

    List<AuthorDtoResponse> authorModelListToauthorDtoList(List<AuthorModel> authorModelList);
}
