package command.commands;

import java.util.Optional;

import command.Command;

public abstract class AbstractSimpleCommand implements Command {

	@Override
	public final void addCommand(Command command) {}

	@Override
	public final void removeCommand(Command command) {}

	@Override
	public final Optional<Command> getChildCommand(String commandName) {
		return Optional.empty();
	}

}
