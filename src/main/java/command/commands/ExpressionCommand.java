package command.commands;

import command.Command;
import command.converter.CommandConverter;

import java.util.List;
import java.util.function.Consumer;

public class ExpressionCommand implements Command {

	private final StringBuilder commandExpression = new StringBuilder();
	private final CommandConverter converter;
	private final String name;
	private final Consumer<Command> action;

	public ExpressionCommand(String name,
													 List<? extends Command> commands,
													 Consumer<Command> action) {
		this.name = name;
		this.action = action;
		this.converter = new CommandConverter(commands);
	}

	@Override
	public void execute() {
		action.accept(converter.convert(commandExpression.toString()));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Parser<?> parser() {
		return context -> {
			commandExpression.append(context).append(" ");
			return this;
		};
	}
}
