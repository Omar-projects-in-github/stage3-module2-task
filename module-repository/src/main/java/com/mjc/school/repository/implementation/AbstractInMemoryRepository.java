package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.IdSequence;
import com.mjc.school.repository.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

abstract class AbstractInMemoryRepository<T extends BaseEntity<K>, K>
        implements BaseRepository<T, K> {

    private final IdSequence<K> sequence;
    private final List<T> data = new ArrayList<>();

    AbstractInMemoryRepository(final IdSequence<K> sequence) {
        this.sequence = sequence;
    }

    abstract void update(T prevState, T nextState);

    @Override
    public List<T> readAll() {
        return List.copyOf(data);
    }

    @Override
    public Optional<T> readById(final K id) {
        return data.stream().filter(hasId(id)).findFirst();
    }

    @Override
    public T create(final T entity) {
        final var generatedId = sequence.generateNewId();
        entity.setId(generatedId);
        data.add(entity);
        return entity;
    }

    @Override
    public T update(final T entity) {
        return readById(entity.getId())
                .map(
                        existingEntity -> {
                            update(existingEntity, entity);
                            return existingEntity;
                        })
                .orElse(null);
    }

    @Override
    public boolean deleteById(final K id) {
        return (id != null) && data.removeIf(hasId(id));
    }

    @Override
    public boolean existById(final K id) {
        return (id != null) && data.stream().anyMatch(hasId(id));
    }

    private Predicate<T> hasId(final K id) {
        return entity -> (id != null) && id.equals(entity.getId());
    }
}
