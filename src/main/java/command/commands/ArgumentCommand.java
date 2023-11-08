package command.commands;

import command.Command;
import command.parser.ArgumentParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ArgumentCommand<T> implements Command {

    protected List<T> arguments;
	private Command command;

    public ArgumentCommand(int argsQuantity, Command command) {
        arguments = new ArrayList<>(argsQuantity);
		this.command = command;
    }

    public void addArgument(String argumentText) {
        arguments.add(getArgumentParser().parse(argumentText));
    }

    protected abstract ArgumentParser<T> getArgumentParser();

	@Override
	public void addCommand(Command command) {
		this.command.addCommand(command);
	}

	@Override
	public void removeCommand(Command commmand) {
		this.command.removeCommand(command);
	}

	@Override
	public Optional<Command> getChildCommand(String commandName) {
		return this.command.getChildCommand(commandName);
	}

}
