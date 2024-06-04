package com.mjc.school.controller.menu.newscommands;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.menu.MenuOptions;
import com.mjc.school.controller.menuinterface.MenuCommands;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.ValidatorException;
import com.mjc.school.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.mjc.school.controller.menu.MenuInputTexts.ENTER_NEWS_ID;
import static com.mjc.school.controller.menu.MenuInputTexts.OPERATION;

@Component
public class GetNewsByIdCommand implements MenuCommands {
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;
    private final Scanner scanner;

    public GetNewsByIdCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController) {
        this.newsController = newsController;
        this.scanner = new Scanner(System.in);
    }
    @Override
    public void execute() {
        System.out.println(OPERATION.getText() + MenuOptions.GET_NEWS_BY_ID.getOptionName());
        Validator validator = new Validator();
        try {
            System.out.println(ENTER_NEWS_ID.getText());
            String newsId = scanner.nextLine();
            if (!validator.validateId(newsId)) {
                throw new ValidatorException(Errors.ERROR_NEWS_ID_FORMAT.getErrorData("", false));
            }

            System.out.println(newsController.readById(Long.parseLong(newsId)));
        } catch (ValidatorException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
