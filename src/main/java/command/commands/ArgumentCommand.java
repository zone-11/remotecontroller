package command.commands;

import command.Command;
import command.parser.ArgumentParser;

import java.util.ArrayList;
import java.util.List;

public abstract class ArgumentCommand<T> implements Command {

    protected List<T> arguments;

    public ArgumentCommand(int argsQuantity) {
        arguments = new ArrayList<>(argsQuantity);
    }

    public void addArgument(String argumentText) {
        arguments.add(getArgumentParser().parse(argumentText));
    }

    protected abstract ArgumentParser<T> getArgumentParser();

}
