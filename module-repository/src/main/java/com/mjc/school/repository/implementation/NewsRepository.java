package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.util.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {
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
    public Optional<NewsModel> readById(Long newsId) {
        return dataSource.getNews().stream()
                .filter(news -> newsId.equals(news.getId()))
                .findFirst();
    }

    @Override
    public NewsModel create(NewsModel model) {
        List<NewsModel> newsModel = dataSource.getNews();
        newsModel.sort(Comparator.comparing(NewsModel::getId));
        if (!newsModel.isEmpty()) {
            model.setId(newsModel.get(newsModel.size() - 1).getId() + 1);
        } else {
            model.setId(1L);
        }
        newsModel.add(model);
        return model;
    }

    @Override
    public NewsModel update(NewsModel model) {
        Optional<NewsModel> newsOptional = readById(model.getId());
        if (newsOptional.isEmpty()) {
            return null;
        }
        NewsModel newsModel = newsOptional.get();
        newsModel.setTitle(model.getTitle());
        newsModel.setContent(model.getContent());
        newsModel.setLastUpdatedDate(model.getLastUpdatedDate());
        newsModel.setAuthorId(model.getAuthorId());
        return newsModel;
    }

    @Override
    public boolean deleteById(Long newsId) {
        return readById(newsId)
                .map(model -> dataSource.getNews().remove(model))
                .orElse(false);
    }

    @Override
    public boolean existById(Long newsId) {
        return dataSource.getNews().stream().anyMatch(news -> newsId.equals(news.getId()));
    }
}
