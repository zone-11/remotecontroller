package command.commands;

import command.Command;

public class CompositeCommand extends CommandDecorator {

    private final ParentalCommand child;

    public CompositeCommand(Command command, Command child) {
        super(command);
        this.child = new ParentalCommand(child, this);
    }

    public Command getChildCommand(String commandName) {
        if (child.getName().equals(commandName)) {
            return child;
        } else if (command instanceof CompositeCommand compositeDecorator) {
            return compositeDecorator.getChildCommand(commandName);
        }
        throw new IllegalArgumentException("command \"%s %s\" does not exist"
                .formatted(this.toString(), commandName));
    }



    public static class ParentalCommand extends  CommandDecorator {

        private final Command parent;

        public ParentalCommand(Command command, Command parent) {
            super(command);
            this.parent = parent;
        }

        @Override
        public void execute() {
            try {
                super.execute();
            } catch (RuntimeException e) {
                throw new RuntimeException("ERROR: command \"" + this + "\"", e);
            }
        }

        @Override
        public String toString() {
            return parent + " " + command;
        }
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

    //always it is the first converter.
    public static class Parser extends Command.Parser {

        public Parser() {
            super();
        }

        public Parser(Command.Parser nextConverter) {
            super(nextConverter);
        }

        @Override
        protected Command hookConvert(Command command, String str) {
            if (command instanceof CompositeCommand compositeCommand) {
                return compositeCommand.getChildCommand(str);
            } else if (command instanceof ParentalCommand parentCommand) {
                nextConverter.convert(parentCommand.command, str);
                return parentCommand;
            }
            return null;
        }
    }
}
