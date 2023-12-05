package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.ResettableArgumentParser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ArgumentCommand<T> extends CommandDecorator<Command> {

    private final List<String> strArgs;
    private final Consumer<List<T>> argAction;
    private final ArgumentParser<T> argParser;
    private final ArgumentCommand<?> childArgCommand;

    private ArgumentCommand(Command command,
                            ArgumentCommand<?> childArgCommand,
                            List<String> strArgs,
                            ArgumentParser<T> argParser,
                            Consumer<List<T>> argAction) {
        super(command);
        this.childArgCommand = childArgCommand;
        this.argParser = argParser;
        this.argAction = argAction;
        this.strArgs = strArgs;
    }

    @Override
    public void execute() {
        if (strArgs.size() == 0) {
            command.execute();
            return;
        }
        if (strArgs.size() != argParser.inputArgsCount()) {
            executeChildArgCommand();
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
                    executeChildArgCommand();
                    reset();
                    return;
                }
            }
        }
        argAction.accept(args);
        reset();
    }

    @Override
    public Function<String, ArgumentCommand> parser() {
        return context -> {
            strArgs.add(context);
            return this;
        };
    }

    private void executeChildArgCommand() {
        if (childArgCommand != null) {
            childArgCommand.execute();
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
            wrappers.add(command -> newInstance(command, parser, argAction));
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


    public static <T> ArgumentCommand<T> newInstance(Command command,
                                                     ArgumentParser<T> parser,
                                                     Consumer<List<T>> action) {
        if (command instanceof ArgumentCommand argCommand) {
            return new ArgumentCommand<>(argCommand.command, argCommand, argCommand.strArgs, parser, action);
        } else {
            return new ArgumentCommand<>(command, null, new ArrayList<String>(), parser,
              action);
        }
    }
}
