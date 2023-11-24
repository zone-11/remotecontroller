package command.commands;

import command.Command;

public class AbstractSimpleCommand implements Command {

    private final String commandName;
    private final Runnable action;

    public AbstractSimpleCommand(String commandName, Runnable action) {
        this.commandName = commandName;
        this.action = action;
    }

    @Override
    public void execute() {
       action.run();
    }

    @Override
    public String getName() {
        return commandName;
    }
}
