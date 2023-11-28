package command.commands;

import command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompositeCommand extends AbstractSimpleCommand {

    private final HashMap<String, ParentalCommand> subCommands = new HashMap<>();

    private CompositeCommand(String name, List<Command> subs) {
        this(name, () -> {}, subs);
    }

    private CompositeCommand(String name, Runnable action, List<Command> subs) {
        super(name, action);
        subs.forEach(sub -> subCommands.put(sub.getName(), new ParentalCommand(sub, this)));
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



    public static class Builder extends Command.Builder<Builder> {

        private final List<Command> subs = new ArrayList<>();

        public Builder thenCommand(Command childCommand) {
            subs.add(childCommand);
            return this;
        }

        public Builder thenCommand(String commandName) {
            thenCommand(Command.simple(commandName, () -> {}));
            return this;
        }

        public Builder thenCommand(String commandName, Runnable action) {
            thenCommand(Command.simple(commandName, action));
            return this;
        }

        @Override
        protected Command hookBuild(String name, Runnable action) {
            return new CompositeCommand(name, action, subs);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    //always it is the first converter.
    public static class Parser extends Command.Parser {


        public Parser(Command.Parser nextConverter) {
            super(nextConverter);
        }

        @Override
        protected Command hookParse(Command command, String str) {
            if (command instanceof CompositeCommand compositeCommand) {
                return compositeCommand.subCommands.get(str);
            } else if (command instanceof ParentalCommand parentCommand) {
                nextConverter.parse(parentCommand.command, str);
                return parentCommand;
            }
            return null;
        }
    }
}
