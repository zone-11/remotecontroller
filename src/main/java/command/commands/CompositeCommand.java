package command.commands;

import command.Command;

import java.util.Optional;

public class CompositeCommand extends CommandDecorator {

    private Command child;

    public CompositeCommand(Command command, Command child) {
        super(command);
        this.child = child;
    }

    public Optional<Command> getChildCommand(String commandName) {
        if (child.getName().equals(commandName)) {
            return Optional.ofNullable(child);
        } else if (command instanceof CompositeCommand compositeDecorator) {
            return compositeDecorator.getChildCommand(commandName);
        }
        throw new IllegalArgumentException("command " + commandName + " does not exist");
    }

}
