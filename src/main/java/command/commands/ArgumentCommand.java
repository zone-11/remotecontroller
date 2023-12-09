package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.ResettableArgumentParser;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class ArgumentCommand<T> extends CommandDecorator<Command> {

    private final List<String> stringArguments;
    private final ArgumentParser<T> argumentParser;
    private final Consumer<List<T>> handler;

    private ArgumentCommand(Command command,
                            ArgumentParser<T> argumentParser,
                            Consumer<List<T>> handler)  {
        super(command);
        this.argumentParser = argumentParser;
        this.handler = handler;

        stringArguments = command instanceof ArgumentCommand argCommand
          ? argCommand.stringArguments
          : new ArrayList<>();
    }

    @Override
    public void execute() {
        if (stringArguments.size() == 0) {
            super.execute();
            return;
        }
        parseArguments().ifPresentOrElse(handler, () -> tryExecuteChild());
        stringArguments.clear();
    }

    private void tryExecuteChild() {
        if (command instanceof ArgumentCommand argumentCommand) {
            super.execute();
        } else {
            throw new IllegalArgumentException(getName() + " doesn't take such arguments");
        }
    }

    private Optional<List<T>> parseArguments() {
        List<T> objArguments = stringArguments.stream()
          .map(argumentParser::parse)
          .filter(Objects::nonNull)
          .toList();

        resetParser();
        return objArguments.size() == argumentParser.outputArgsCount()
          ? Optional.of(objArguments)
          : Optional.empty();
    }

    private void resetParser() {
        if (argumentParser instanceof ResettableArgumentParser<?> resetParser) {
            resetParser.reset();
        }
    }

    @Override
    public Function<String, ArgumentCommand> parser() {
        return context -> {
            stringArguments.add(context);
            return this;
        };
    }



    public static class Builder extends Command.Builder<Builder> {

        private final List<Function<Command, Command>> wrappers = new ArrayList<>();

        public <T> Builder argAction(ArgumentParser<T> parser,
                                     Consumer<List<T>> argAction) {
            wrappers.add(command -> new ArgumentCommand<>(command, parser, argAction));
            return self();
        }

        @Override
        protected Command hookBuild(String name, Runnable action) {
            Command command = new Command.Simple(name, action);
            for (Function<Command, Command> wrapper : wrappers) {
                command = wrapper.apply(command);
            }

            return command;
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
