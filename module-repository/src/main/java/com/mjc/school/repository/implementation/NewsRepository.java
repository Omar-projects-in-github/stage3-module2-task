package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.util.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {
    private final DateTimeFormatter MY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private final DataSource dataSource;

    @Autowired
    public NewsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<NewsModel> readAll() {
        return dataSource.getNews();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return Optional.of(readAll()
                        .stream()
                        .filter(newsModel -> id.equals(newsModel.getId()))
                        .findFirst())
                .orElse(null);
    }

    @Override
    public NewsModel create(NewsModel entity) {
        Long id = 1L;
        if (!readAll().isEmpty()) {
            id = readAll().get(readAll().size()-1).getId() + 1;
        }
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));
        entity.setId(id);
        entity.setCreateDate(dateTime);
        entity.setLastUpdateDate(dateTime);
        readAll().add(entity);
        return entity;
    }

    @Override
    public NewsModel update(NewsModel entity) {
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));

        int index = -1;
        for (NewsModel newsModel: readAll()) {
            if (Objects.equals(entity.getId(), newsModel.getId())) {
                index = readAll().indexOf(newsModel);
            }
        }

        if (index>=0) {
            readAll().get(index).setTitle(entity.getTitle());
            readAll().get(index).setContent(entity.getContent());
            readAll().get(index).setLastUpdateDate(dateTime);
            readAll().get(index).setAuthorId(entity.getAuthorId());
            return readAll().get(index);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return readById(id)
                .map(newsModel -> readAll().remove(newsModel))
                .orElse(false);
    }

    @Override
    public boolean existById(Long id) {
        return readAll()
                .stream()
                .anyMatch(newsModel -> id.equals(newsModel.getId()));
    }
}