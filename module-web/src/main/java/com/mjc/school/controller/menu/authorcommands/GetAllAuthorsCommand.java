package com.mjc.school.controller.menu.authorcommands;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.menu.MenuOptions;
import com.mjc.school.controller.menuinterface.MenuCommands;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.stereotype.Component;

import static com.mjc.school.controller.menu.MenuInputTexts.OPERATION;

@Component
public class GetAllAuthorsCommand implements MenuCommands {
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;

    public GetAllAuthorsCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController) {
        this.authorController = authorController;
    }

    @Override
    public void execute() {
        System.out.println(OPERATION.getText() + MenuOptions.GET_ALL_AUTHORS.getOptionName());
        authorController.readAll().forEach(System.out::println);
    }
}
