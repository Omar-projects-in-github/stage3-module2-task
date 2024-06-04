package com.mjc.school.repository.config;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.IdSequence;
import com.mjc.school.repository.implementation.AuthorInMemoryRepository;
import com.mjc.school.repository.implementation.InMemoryIdSequence;
import com.mjc.school.repository.implementation.NewsInMemoryRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
public class RepositoryConfig {

    private static final String AUTHORS_FILE_NAME = "authors";
    private static final String CONTENT_FILE_NAME = "content";
    private static final String NEWS_FILE_NAME = "news";

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public IdSequence<Long> longIdSequence() {
        return new InMemoryIdSequence<>(1L, prev -> prev + 1);
    }

    @Bean
    public BaseRepository<Author, Long> authorRepository(final IdSequence<Long> idSequence) {
        final var authorRepository = new AuthorInMemoryRepository(idSequence);
        initializeAuthorsRepository(authorRepository);
        return authorRepository;
    }

    @Bean
    public BaseRepository<News, Long> newsRepository(
            final IdSequence<Long> idSequence, final BaseRepository<Author, Long> authorRepository) {
        final var newsRepository = new NewsInMemoryRepository(idSequence);
        initializeNewsRepository(newsRepository, authorRepository);
        return newsRepository;
    }

    private void initializeAuthorsRepository(final BaseRepository<Author, Long> authorRepository) {
        final var authors = loadDataFromFile(AUTHORS_FILE_NAME);
        Stream.generate(
                        () -> {
                            var date = getRandomDate();
                            return new Author(null, getRandomElement(authors), date, date);
                        })
                .limit(20)
                .forEach(authorRepository::create);
    }

    private void initializeNewsRepository(
            final BaseRepository<News, Long> newsRepository,
            final BaseRepository<Author, Long> authorRepository) {
        final List<String> titles = loadDataFromFile(NEWS_FILE_NAME);
        final List<String> contents = loadDataFromFile(CONTENT_FILE_NAME);
        final List<Author> authors = authorRepository.readAll();

        Stream.generate(
                        () -> {
                            var date = getRandomDate();
                            return new News(
                                    null,
                                    getRandomElement(titles),
                                    getRandomElement(contents),
                                    date,
                                    date,
                                    getRandomElement(authors).getId());
                        })
                .limit(20)
                .forEach(newsRepository::create);
    }

    private List<String> loadDataFromFile(final String filename) {
        List<String> lines = Collections.emptyList();

        try (final var reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     Thread.currentThread().getContextClassLoader().getResourceAsStream(filename),
                                     StandardCharsets.UTF_8))) {
            lines = reader.lines().toList();
            if (lines.isEmpty()) {
                throw new IllegalStateException("File must not be empty");
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private <T> T getRandomElement(final List<? extends T> strings) {
        return strings.get(new Random().nextInt(strings.size()));
    }

    private LocalDateTime getRandomDate() {
        Random random = new Random();
        int endDay = 30;
        LocalDate day = LocalDate.now().plusDays(random.nextInt(endDay));
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        LocalTime time = LocalTime.of(hour, minute, second);
        return LocalDateTime.of(day, time);
    }
}
