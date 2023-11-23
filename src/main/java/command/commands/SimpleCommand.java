package command.commands;


import command.Command;

public class SimpleCommand implements Command {

    private final String name;
    private final Runnable action;

    public SimpleCommand(String name) {
        this.name = name;
        this.action = () -> {};
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

    @Override
    public String toString() {
        return name;
    }
}
