package com.mjc.school.repository.implementation;

import com.mjc.school.repository.IdSequence;
import com.mjc.school.repository.model.Author;

public class AuthorInMemoryRepository extends AbstractInMemoryRepository<Author, Long> {

    public AuthorInMemoryRepository(final IdSequence<Long> sequence) {
        super(sequence);
    }

    @Override
    void update(final Author prevState, final Author nextState) {
        prevState.setName(nextState.getName());
        prevState.setLastUpdatedDate(nextState.getLastUpdatedDate());
    }
}
