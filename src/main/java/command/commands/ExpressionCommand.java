package command.commands;

import command.Command;
import command.converter.CommandConverter;

import java.util.List;
import java.util.function.Consumer;

public class ExpressionCommand<T extends Command> implements Command {

	private final StringBuilder commandExpression = new StringBuilder();
	private final CommandConverter converter;

	private final String name;
	private final Consumer<T> action;

	public ExpressionCommand(String name,
													 Consumer<T> action,
													 List<? extends T> acceptedCommands) {
		this.name = name;
		this.action = action;
		converter = new CommandConverter(acceptedCommands);
	}

	@Override
	public void execute() {
		converter.convert(commandExpression.toString());
	}

	@Override
	public String getName() {
		return name;
	}

	public static class Parser extends Command.Parser {

		public Parser(Command.Parser nextConverter) {
			super(nextConverter);
		}

		@Override
		protected Command hookParse(Command command, String str) {
			if (command instanceof ExpressionCommand expCommand) {
				expCommand.commandExpression.append(str).append(" ");
				return expCommand;
			}
			return null;
		}
	}
}
