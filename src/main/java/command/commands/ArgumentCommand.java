package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.ResettableArgumentParser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArgumentCommand<T> extends CommandDecorator {

    private final Consumer<List<T>> action;
    private final ArgumentParser<T> argParser;
    private final List<T> args = new ArrayList<>();
    private final int argsQuantity;
    private int currentArgsQuantity = 0;

    public ArgumentCommand(Command command,
                           ArgumentParser<T> argParser,
                           Consumer<List<T>> action) {
        super(command);
        this.action = action;
        this.argParser = argParser;
        this.argsQuantity = argParser.getArgumentsNumber();
    }

    @Override
    public void execute() {
        if (args.size() == argsQuantity
            && argsQuantity == currentArgsQuantity) {
            action.accept(args);
        } else if (!(command instanceof ArgumentCommand<?>)
                    && currentArgsQuantity > 0){
            throw new IllegalArgumentException("command " + "\"" + this + "\"" + " does not accept such arguments");
        } else {
            super.execute();
        }
        reset();
    }

    public void addArgument(String arg) {
       T parsingArg = argParser.parse(arg);
       if (args.size() + 1 <= argsQuantity
            && parsingArg != null) {
           args.add(parsingArg);
       }
       if (command instanceof ArgumentCommand<?> argCommand) {
           argCommand.addArgument(arg);
       }

       ++currentArgsQuantity;
    }

    private void reset() {
        this.args.clear();
        currentArgsQuantity = 0;
        if (argParser instanceof ResettableArgumentParser<?> resParser) {
            resParser.reset();
        }

        if (command instanceof ArgumentCommand<?> argCommand) {
            argCommand.reset();
        }
    }



    public static class Parser extends Command.Parser {

        public Parser(Command.Parser nextConverter) {
            super(nextConverter);
        }

        public Parser() {
            super();
        }

        @Override
        protected Command hookConvert(Command command, String str) {
            if (command instanceof ArgumentCommand<?> argCommand) {
                argCommand.addArgument(str);
                return argCommand;
            }
            return null;
        }
    }

    public static class Builder extends CommandDecorator.Builder {

        public Builder(String name) {
            super(name);
        }

        public Builder(String name, Runnable action) {
            super(name, action);
        }

        public <T> Builder arguments(ArgumentParser<T> parser,
                                     Consumer<List<T>> action) {
            command = new ArgumentCommand<>(command, parser, action);
            return this;
        }
    }
}
