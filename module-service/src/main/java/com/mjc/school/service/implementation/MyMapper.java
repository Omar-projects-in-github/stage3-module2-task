package com.mjc.school.service.implementation;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyMapper implements Mapper {
    @Override
    public NewsDtoResponse newsModelToNewsDto(NewsModel newsModel) {
        NewsDtoResponse newsDto = new NewsDtoResponse(newsModel.getTitle(),
                newsModel.getContent(), newsModel.getAuthorId());
        newsDto.setId(newsModel.getId());
        return newsDto;
    }

    @Override
    public NewsModel newsDtoToNewsModel(NewsDtoRequest newsDto) {
        NewsModel newsModel = new NewsModel(newsDto.getTitle(),
                newsDto.getContent(), newsDto.getAuthorId());
        newsModel.setId(newsDto.getId());
        return newsModel;
    }

    @Override
    public List<NewsDtoResponse> newsModelListToNewsDtoList(List<NewsModel> newsModelList) {
        List<NewsDtoResponse> newsDtoResponseList = new ArrayList<>();
        for (NewsModel newsModel: newsModelList) {
            NewsDtoResponse newsDtoResponse = newsModelToNewsDto(newsModel);
            newsDtoResponseList.add(newsDtoResponse);
        }
        return newsDtoResponseList;
    }

    @Override
    public AuthorDtoResponse authorModelToAuthorDto(AuthorModel authorModel) {
        AuthorDtoResponse authorDto = new AuthorDtoResponse(authorModel.getName());
        authorDto.setId(authorModel.getId());
        return authorDto;
    }

    @Override
    public AuthorModel authorDtoToAuthorModel(AuthorDtoRequest authorDto) {
        AuthorModel authorModel = new AuthorModel(authorDto.getName());
        authorModel.setId(authorDto.getId());
        return authorModel;
    }

    @Override
    public List<AuthorDtoResponse> authorModelListToauthorDtoList(List<AuthorModel> authorModelList) {
        List<AuthorDtoResponse> authorDtoResponseList = new ArrayList<>();
        for (AuthorModel authorModel: authorModelList) {
            AuthorDtoResponse authorDtoResponse = authorModelToAuthorDto(authorModel);
            authorDtoResponseList.add(authorDtoResponse);
        }
        return authorDtoResponseList;
    }
}
