package com.mjc.school.repository.implementation;

import com.mjc.school.repository.IdSequence;
import com.mjc.school.repository.model.News;

public class NewsInMemoryRepository extends AbstractInMemoryRepository<News, Long> {

    public NewsInMemoryRepository(final IdSequence<Long> sequence) {
        super(sequence);
    }

    @Override
    void update(final News prevState, final News nextState) {
        prevState.setTitle(nextState.getTitle());
        prevState.setContent(nextState.getContent());
        prevState.setLastUpdatedDate(nextState.getLastUpdatedDate());
        prevState.setAuthorId(nextState.getAuthorId());
    }
}
