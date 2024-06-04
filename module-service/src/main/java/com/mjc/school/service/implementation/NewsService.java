package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotations.ValidatingNews;
import com.mjc.school.service.annotations.ValidatingNewsId;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.Errors;
import com.mjc.school.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {
    private final BaseRepository<News, Long> newsRepository;
    private final BaseRepository<AuthorModel, Long> authorRepository;

    @Autowired
    public NewsService(BaseRepository<News, Long> newsRepository,
                       BaseRepository<AuthorModel, Long> authorRepository) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return MyMapper.INSTANCE.newsModelListToNewsDtoList(newsRepository.readAll());
    }

    @ValidatingNewsId
    @Override
    public NewsDtoResponse readById(Long id) {
        return newsRepository.readById(id)
                .map(MyMapper.INSTANCE::newsModelToNewsDto)
                .orElseThrow(() -> new NotFoundException(Errors.ERROR_NEWS_ID_NOT_EXIST.getErrorData(String.valueOf(id), true)));
    }

    @ValidatingNews
    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        for (AuthorModel authorModel: authorRepository.readAll()) {
            if (Objects.equals(createRequest.getAuthorId(), authorModel.getId())) {
                News news = newsRepository.create(MyMapper.INSTANCE.newsDtoToNewsModel(createRequest));
                return MyMapper.INSTANCE.newsModelToNewsDto(news);
            }
        }
        return null;
    }

    @ValidatingNews
    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        for (AuthorModel authorModel: authorRepository.readAll()) {
            if (Objects.equals(updateRequest.getAuthorId(), authorModel.getId())
                    && readById(updateRequest.getId())!=null) {
                News news = newsRepository.update(MyMapper.INSTANCE.newsDtoToNewsModel(updateRequest));
                return MyMapper.INSTANCE.newsModelToNewsDto(news);
            }
        }
        return null;
    }

    @ValidatingNewsId
    @Override
    public boolean deleteById(Long id) {
        if (readById(id)!=null) {
            return newsRepository.deleteById(id);
        } else {
            return false;
        }
    }
}
