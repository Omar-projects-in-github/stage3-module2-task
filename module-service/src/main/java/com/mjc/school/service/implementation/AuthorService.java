package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotations.ValidatingAuthor;
import com.mjc.school.service.annotations.ValidatingAuthorId;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.Errors;
import com.mjc.school.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final BaseRepository<AuthorModel, Long> authorRepository;

    @Autowired
    public AuthorService(BaseRepository<AuthorModel, Long> authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return MyMapper.INSTANCE.authorModelListToauthorDtoList(authorRepository.readAll());
    }

    @ValidatingAuthorId
    @Override
    public AuthorDtoResponse readById(Long id) {
        return authorRepository.readById(id)
                .map(MyMapper.INSTANCE::authorModelToAuthorDto)
                .orElseThrow(() -> new NotFoundException(Errors.ERROR_AUTHOR_ID_NOT_EXIST.getErrorData(String.valueOf(id), true)));
    }

    @ValidatingAuthor
    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        AuthorModel authorModel = authorRepository.create(MyMapper.INSTANCE.authorDtoToAuthorModel(createRequest));
        return MyMapper.INSTANCE.authorModelToAuthorDto(authorModel);
    }

    @ValidatingAuthor
    @Override
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        if (readById(updateRequest.getId())!=null) {
            AuthorModel authorModel = authorRepository.update(MyMapper.INSTANCE.authorDtoToAuthorModel(updateRequest));
            return MyMapper.INSTANCE.authorModelToAuthorDto(authorModel);
        }
        return null;
    }

    @ValidatingAuthorId
    @Override
    public boolean deleteById(Long id) {
        if (readById(id)!=null) {
            return authorRepository.deleteById(id);
        } else {
            return false;
        }
    }
}
