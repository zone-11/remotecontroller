package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArgumentCommand<T> extends CommandDecorator {

    private Consumer<List<T>> action;
    private ArgumentParser<T> argParser;
    private List<T> args = new ArrayList<>();
    private int argsQuantity;
    private int currentArgsQuantity = 0;

    public ArgumentCommand(Command command,
                           ArgumentParser<T> argParser,
                           Consumer<List<T>> action,
                           int argsQuantity) {
        super(command);
        this.action = action;
        this.argParser = argParser;
        this.argsQuantity = argsQuantity;
    }

    @Override
    public void execute() {
        if (args.size() == argsQuantity
            && argsQuantity == currentArgsQuantity) {
            action.accept(args);
        } else if (!(command instanceof ArgumentCommand<?>)
                    && currentArgsQuantity > 0){
            throw new IllegalArgumentException("command \"" + this + "\" does not accept such arguments");
        } else {
            super.execute();
        }
        removeArguments();
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

    private void removeArguments() {
        this.args.clear();
        currentArgsQuantity = 0;
        if (command instanceof ArgumentCommand<?> argCommand) {
            argCommand.removeArguments();
        }
    }
}
