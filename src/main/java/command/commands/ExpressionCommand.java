package command.commands;

import command.Command;
import command.commands.adapter.CommandPacket;
import command.converter.CommandConverter;
import command.converter.DefaultCommandConverter;

import java.util.function.Consumer;

public class ExpressionCommand implements Command {

	private static final CommandConverter CONVERTER = new DefaultCommandConverter();

	private final StringBuilder commandExpression = new StringBuilder();
	private final String name;
	private final Consumer<Command> action;

	public ExpressionCommand(String name, Consumer<Command> action) {
		this.name = name;
		this.action = action;
	}

	@Override
	public void execute() {
		var command = (CommandPacket)CONVERTER.parse(commandExpression.toString());

		command.forEach(innerCommand -> {
			if (innerCommand instanceof ExpressionCommand) {
				throw new IllegalArgumentException("ExpressionCommand cannot execute ExpressionCommand");
			}
		});

		action.accept(command);
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
		protected Command hookConvert(Command command, String str) {
			if (command instanceof ExpressionCommand expCommand) {
				expCommand.commandExpression.append(str + " ");
				return expCommand;
			}
			return null;
		}
	}
}
