package command.builders;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.commands.ArgumentCommand;

import java.util.List;
import java.util.function.Consumer;

public class ArgumentCommandBuilder extends Command.Builder<ArgumentCommandBuilder> {

    public ArgumentCommandBuilder(String name, Runnable action) {
        super(name, action);
    }

    public ArgumentCommandBuilder(String name) {
        super(name);
    }

    public <T> ArgumentCommandBuilder withArgument(ArgumentParser<T> parser,
                                                   int argsQuantity,
                                                   Consumer<List<T>> action) {
        command = new ArgumentCommand<>(command, parser, action, argsQuantity);
        return this;
    }

    @Override
    protected Command.Builder<ArgumentCommandBuilder> self() {
       return this;
    }
}
