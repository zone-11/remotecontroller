package command.commands;


public class SimpleCommand extends AbstractSimpleCommand {

    public SimpleCommand(String name) {
        super(name, () -> {});
    }

    public SimpleCommand(String name, Runnable action) {
       super(name, action);
    }
}
