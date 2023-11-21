package command.commands;

import command.Command;

import java.util.Optional;

public class CompositeCommand extends CommandDecorator {

    private final Command child;

    public CompositeCommand(Command command, Command child) {
        super(command);
        this.child = child;
        child.setParent(this);
    }

    public Optional<Command> getChildCommand(String commandName) {
        if (child.getName().equals(commandName)) {
            return Optional.ofNullable(child);
        } else if (command instanceof CompositeCommand compositeDecorator) {
            return compositeDecorator.getChildCommand(commandName);
        }
        throw new IllegalArgumentException("command " + commandName + " does not exist");
    }

    public static class Builder extends Command.Builder {

        public Builder(String commandName) {
            super(commandName);
        }

        public Builder(String commandName, Runnable action) {
            super(commandName, action);
        }

        public Builder thenCommand(Command childCommand) {
            command = new CompositeCommand(command, childCommand);
            return this;
        }

        public Builder thenCommand(String commandName) {
            command = new CompositeCommand(command, new SimpleCommand(commandName));
            return this;
        }

        public Builder thenCommand(String commandName, Runnable action) {
            command = new CompositeCommand(command, new SimpleCommand(commandName, action));
            return this;
        }

    }

}
