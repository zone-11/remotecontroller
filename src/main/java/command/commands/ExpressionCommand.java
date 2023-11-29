package command.commands;

import command.Command;
import command.converter.CommandConverter;
import command.converter.DefaultCommandConverter;

import java.util.List;
import java.util.function.Consumer;

public class ExpressionCommand implements Command {

	private final StringBuilder commandExpression = new StringBuilder();
	private final CommandConverter converter;

	private final String name;
	private final Consumer<Command> action;

	public ExpressionCommand(String name,
													 Consumer<Command> action,
													 List<Command> acceptedCommands) {
		this.name = name;
		this.action = action;
		converter = new DefaultCommandConverter(acceptedCommands);
	}

	@Override
	public void execute() {
		action.accept(converter.convert(commandExpression.toString()));
		commandExpression.delete(0, commandExpression.length());
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
