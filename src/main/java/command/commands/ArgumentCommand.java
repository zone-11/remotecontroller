package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.ResettableArgumentParser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ArgumentCommand<T> extends CommandDecorator<Command> {

    private final Consumer<List<T>> action;
    private final ArgumentParser<T> argParser;
    private final List<String> strArgs;

    public ArgumentCommand(Command command,
                           ArgumentParser<T> argParser,
                           Consumer<List<T>> action) {
        super(command);
        this.action = action;
        this.argParser = argParser;

        if (command instanceof ArgumentCommand<?> argCommand) {
            strArgs = argCommand.strArgs;
        } else {
            strArgs = new ArrayList<>();
        }
    }

    @Override
    public void execute() {
        if (strArgs.size() != argParser.inputArgsCount()) {
            subExecute();
            return;
        }

        List<T> args = new ArrayList<>();
        int nulls = 0;
        for (String arg : strArgs) {
            T parsingArg = argParser.parse(arg);
            if (parsingArg != null) {
                args.add(parsingArg);
            } else {
                ++nulls;
                if (nulls > argParser.inputArgsCount() - argParser.outputArgsCount()) {
                    subExecute();
                    reset();
                    return;
                }
            }
        }
        action.accept(args);
        reset();
    }

    @Override
    public Function<String, ArgumentCommand> parser() {
        return context -> {
            strArgs.add(context);
            return this;
        };
    }

    private void subExecute() {
        if (strArgs.size() == 0 || command instanceof ArgumentCommand) {
            command.execute();
        } else {
            throw new IllegalArgumentException("command doesn't take such arguments");
        }
    }

    private void reset() {
        strArgs.clear();
        if (argParser instanceof ResettableArgumentParser<?> resetParser) {
            resetParser.reset();
        }
    }


    public static class Builder extends Command.Builder<Builder> {

        private final List<Function<Command, Command>> wrappers = new ArrayList<>();

        public <T> Builder argAction(ArgumentParser<T> parser,
                                     Consumer<List<T>> argAction) {
            wrappers.add(command -> new ArgumentCommand(command, parser, argAction));
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
