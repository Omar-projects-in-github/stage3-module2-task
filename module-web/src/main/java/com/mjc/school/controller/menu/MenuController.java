package com.mjc.school.controller.menu;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.menu.authorcommands.*;
import com.mjc.school.controller.menu.newscommands.*;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.mjc.school.controller.menu.MenuInputTexts.ENTER_NUMBER_OF_OPERATION;

@Component
public class MenuController {
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;
    private final CommandsExecutor commandsExecutor;
    private final Scanner scanner;

    @Autowired
    public MenuController(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController,
                             BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController,
                             CommandsExecutor commandsExecutor) {
        this.newsController = newsController;
        this.authorController = authorController;
        this.commandsExecutor = commandsExecutor;
        scanner = new Scanner(System.in);
    }

    public void runApp() {
        while (true) {
            // program prints out app menu
            System.out.println(ENTER_NUMBER_OF_OPERATION.getText());
            for (MenuOptions options: MenuOptions.values()) {
                System.out.println(options.getOptionCode() + " - " + options.getOptionName());
            }
            // client chooses menu option
            switch (scanner.nextLine()) {
                case "1" -> commandsExecutor.executeCommand(new GetAllNewsCommand(newsController));
                case "2" -> commandsExecutor.executeCommand(new GetNewsByIdCommand(newsController));
                case "3" -> commandsExecutor.executeCommand(new CreateNewsCommand(newsController));
                case "4" -> commandsExecutor.executeCommand(new UpdateNewsCommand(newsController));
                case "5" -> commandsExecutor.executeCommand(new RemoveNewsByIdCommand(newsController));
                case "6" -> commandsExecutor.executeCommand(new GetAllAuthorsCommand(authorController));
                case "7" -> commandsExecutor.executeCommand(new GetAuthorByIdCommand(authorController));
                case "8" -> commandsExecutor.executeCommand(new CreateAuthorCommand(authorController));
                case "9" -> commandsExecutor.executeCommand(new UpdateAuthorCommand(authorController));
                case "10" -> commandsExecutor.executeCommand(new RemoveAuthorByIdCommand(authorController, newsController));
                case "0" -> System.exit(0);
                default -> System.out.println(Errors.ERROR_COMMAND_NOT_FOUND.getErrorMessage());
            }
        }
    }
}
