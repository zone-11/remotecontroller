package command.commands;

import command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CompositeCommand extends AbstractSimpleCommand {

    private final HashMap<String, ParentalCommand> subCommands = new HashMap<>();

    private CompositeCommand(String name, List<Command> subs) {
        this(name, () -> {
        }, subs);
    }

    private CompositeCommand(String name, Runnable action, List<Command> subs) {
        super(name, action);
        subs.forEach(sub -> subCommands.put(sub.getName(), new ParentalCommand(sub, this)));
    }

    private Optional<Command> subCommand(String name) {
        return Optional.ofNullable(subCommands.get(name));
    }

    @Override
    public Parser<Command> parser() {
        return context -> subCommand(context)
          .orElseThrow(() -> new IllegalArgumentException("command not found"));
    }


    public static class ParentalCommand extends CommandDecorator {

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
        public Parser<Command> parser() {
            return context -> {
                command.parser().parse(context);
                return this;
            };
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
            thenCommand(commandName, () -> {});
            return this;
        }

        public Builder thenCommand(String commandName, Runnable action) {
            thenCommand(new Command.Simple(commandName, action));
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
}

