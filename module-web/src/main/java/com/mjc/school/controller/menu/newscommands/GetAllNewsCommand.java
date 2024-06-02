package com.mjc.school.controller.menu.newscommands;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.menu.MenuOptions;
import com.mjc.school.controller.menuinterface.MenuCommands;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.stereotype.Component;

import static com.mjc.school.controller.menu.MenuInputTexts.OPERATION;

@Component
public class GetAllNewsCommand implements MenuCommands {
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;

    public GetAllNewsCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController) {
        this.newsController = newsController;
    }

    @Override
    public void execute() {
        System.out.println(OPERATION.getText() + MenuOptions.GET_ALL_NEWS.getOptionName());
        newsController.readAll().forEach(System.out::println);
    }
}
