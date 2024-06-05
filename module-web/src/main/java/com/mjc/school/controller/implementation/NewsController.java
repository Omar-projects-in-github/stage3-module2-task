package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParameter;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long> {

    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService;

    @Autowired
    public NewsController(final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService) {
        this.newsService = newsService;
    }

    @Override
    @CommandHandler(operation = 1)
    public List<NewsDtoResponse> readAll() {
        return newsService.readAll();
    }

    @Override
    @CommandHandler(operation = 2)
    public NewsDtoResponse readById(@CommandParameter(name = "id") Long id) {
        return newsService.readById(id);
    }

    @Override
    @CommandHandler(operation = 3)
    public NewsDtoResponse create(@CommandBody NewsDtoRequest dtoRequest) {
        return newsService.create(dtoRequest);
    }

    @Override
    @CommandHandler(operation = 4)
    public NewsDtoResponse update(@CommandBody NewsDtoRequest dtoRequest) {
        return newsService.update(dtoRequest);
    }

    @Override
    @CommandHandler(operation = 5)
    public boolean deleteById(@CommandParameter(name = "id") Long id) {
        return newsService.deleteById(id);
    }
}