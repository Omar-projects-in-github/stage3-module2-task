package com.mjc.school.controller.menu;

import com.mjc.school.controller.menuinterface.MenuCommands;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandsExecutor {
    private final List<MenuCommands> commands = new ArrayList<>();

    public void executeCommand(MenuCommands command) {
        commands.add(command);
        command.execute();
    }
}
