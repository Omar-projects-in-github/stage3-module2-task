package com.mjc.school.repository.aspects;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.util.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Aspect
@Component
public class DeleteAspect {
    private final DataSource dataSource;

    @Autowired
    private DeleteAspect(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Before("@annotation(com.mjc.school.repository.annotation.OnCascadeDelete) && args(id)")
    public void cascadeDelete(Long id) {
        List<NewsModel> newsModelList = dataSource.getNews();
        List<NewsModel> deletedList = newsModelList
                .stream()
                .filter(news -> Objects.equals(news.getAuthorId(), id))
                .toList();
        newsModelList.removeAll(deletedList);
    }
}
