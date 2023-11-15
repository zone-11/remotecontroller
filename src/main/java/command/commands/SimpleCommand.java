package command.commands;

import command.Command;

public class SimpleCommand implements Command {

    private String name;
    private Runnable action = () -> {};

    public SimpleCommand(String name) {
        this.name = name;
    }

    public SimpleCommand(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    @Override
    public void execute() {
       action.run();
    }

    @Override
    public String getName() {
        return name;
    }

}
