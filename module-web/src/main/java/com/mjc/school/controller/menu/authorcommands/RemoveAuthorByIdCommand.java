package com.mjc.school.controller.menu.authorcommands;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.menu.MenuOptions;
import com.mjc.school.controller.menuinterface.MenuCommands;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.ValidatorException;
import com.mjc.school.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.mjc.school.controller.menu.MenuInputTexts.ENTER_AUTHOR_ID;
import static com.mjc.school.controller.menu.MenuInputTexts.OPERATION;

@Component
public class RemoveAuthorByIdCommand implements MenuCommands {
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;
    private final Scanner scanner;

    public RemoveAuthorByIdCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController,
                                   BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController) {
        this.authorController = authorController;
        this.newsController = newsController;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println(OPERATION.getText() + MenuOptions.REMOVE_AUTHOR_BY_ID.getOptionName());
        Validator validator = new Validator();
        try {
            System.out.println(ENTER_AUTHOR_ID);
            String authorId = scanner.nextLine();
            if (!validator.validateId(authorId)) {
                throw new ValidatorException(Errors.ERROR_AUTHOR_ID_FORMAT.getErrorData("", false));
            }

            System.out.println(authorController.deleteById(Long.parseLong(authorId)));
        } catch (ValidatorException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
