package com.mjc.school.controller.menu.authorcommands;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.menu.MenuOptions;
import com.mjc.school.controller.menuinterface.MenuCommands;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.ValidatorException;
import com.mjc.school.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.mjc.school.controller.menu.MenuInputTexts.*;

@Component
public class UpdateAuthorCommand implements MenuCommands {
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;
    private final Scanner scanner;

    public UpdateAuthorCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController) {
        this.authorController = authorController;
        this.scanner = new Scanner(System.in);
    }
    @Override
    public void execute() {
        System.out.println(OPERATION.getText() + MenuOptions.UPDATE_AUTHOR.getOptionName());
        Validator validator = new Validator();
        try {
            System.out.println(ENTER_AUTHOR_ID);
            String id = scanner.nextLine();
            if (!validator.validateId(id)) {
                throw new ValidatorException(Errors.ERROR_AUTHOR_ID_FORMAT.getErrorData("", false));
            }
            validator.validateAuthorId(Long.parseLong(id));

            System.out.println(ENTER_AUTHOR_NAME);
            String name = scanner.nextLine();

            AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest(Long.parseLong(id), name);
            System.out.println(authorController.update(authorDtoRequest));
        } catch (ValidatorException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
