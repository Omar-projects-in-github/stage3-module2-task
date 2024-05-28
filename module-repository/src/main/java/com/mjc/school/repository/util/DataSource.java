package com.mjc.school.repository.util;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mjc.school.repository.model.data.AuthorData.getAuthorData;
import static com.mjc.school.repository.model.data.NewsData.getNewsData;

@Component
public class DataSource {
    /*private final List<NewsModel> news;
    private final List<AuthorModel> authors;
    public static final int GENERATE_DATA_LIMIT = 20;
    public static final String AUTHOR_FILE = "authors";

    @Autowired
    private DataSource() {
        authors = getAuthorData().getAuthorList();
        news = getNewsData(authors).getNewsList();
    }

    private List<AuthorModel> initAuthors() {
        List<AuthorModel> authors = new ArrayList<>();
        for (long i = 1; i <= GENERATE_DATA_LIMIT; i++) {
            LocalDateTime localDateTime = Utils.getRandomDate();
            authors.add(new AuthorData()
                    .setId(i)
                    .setName(Utils.getRandomContentByFilePath(AUTHOR_FILE))
                    .setCreateDate(localDateTime)
                    .setLastUpdateDate(localDateTime)
                    .build());
        }
        return authors;
    }

    private List<NewsModel> initNews() {
        List<NewsModel> news = new ArrayList<>();
        for (long i = 1; i <= Constants.GENERATE_DATA_LIMIT ; i++) {
            LocalDateTime localDateTime = Utils.getRandomDate();
            news.add(new NewsBuilder()
                    .setId(i)
                    .setTitle(Utils.getRandomData(Constants.NEWS_FILE))
                    .setContent(Utils.getRandomData(Constants.CONTENT_FILE))
                    .setCreateDate(localDateTime)
                    .setLastUpdateDate(localDateTime)
                    .setAuthorId(authorModelList.get(random.nextInt(authorModelList.size())).getId())
                    .build());
        }
        return news;
    }

    public static DataSource getInstance() {
        return LazyDataSource.INSTANCE;
    }

    public List<NewsModel> getNews() {
        return news;
    }

    public List<AuthorModel> getAuthors() {
        return authors;
    }

    private static class LazyDataSource {
        static final DataSource INSTANCE = new DataSource();
    }*/
    private final List<NewsModel> news;
    private final List<AuthorModel> authors;

    @Autowired
    private DataSource() {
        authors = getAuthorData().getAuthorList();
        news = getNewsData(authors).getNewsList();
    }

    public static DataSource getInstance() {
        return LazyDataSource.INSTANCE;
    }

    public List<NewsModel> getNews() {
        return news;
    }

    public List<AuthorModel> getAuthors() {
        return authors;
    }

    private static class LazyDataSource {
        static final DataSource INSTANCE = new DataSource();
    }
}
