package command.commands;

import command.Command;

import java.util.HashMap;

public class CompositeCommand extends AbstractSimpleCommand {

    private final HashMap<String, ParentalCommand> subCommands =
        new HashMap<>();

    private CompositeCommand(String name) {
        super(name, () -> {});
    }

    private CompositeCommand(String name, Runnable action) {
        super(name, action);
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



    public static class Builder implements Command.Builder {

        private final CompositeCommand command;

        public Builder(String commandName) {
            command = new CompositeCommand(commandName);
        }

        public Builder(String commandName, Runnable action) {
            command = new CompositeCommand(commandName, action);
        }

        public Builder thenCommand(Command childCommand) {
            command.subCommands.put(childCommand.getName(),
                    new ParentalCommand(childCommand, command));
            return this;
        }

        public Builder thenCommand(String commandName) {
            thenCommand(new SimpleCommand(commandName));
            return this;
        }

        public Builder thenCommand(String commandName, Runnable action) {
            thenCommand(new SimpleCommand(commandName, action));
            return this;
        }

        @Override
        public Command build() {
           return command;
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
                return compositeCommand.subCommands.get(str);
            } else if (command instanceof ParentalCommand parentCommand) {
                nextConverter.convert(parentCommand.command, str);
                return parentCommand;
            }
            return null;
        }
    }
}
