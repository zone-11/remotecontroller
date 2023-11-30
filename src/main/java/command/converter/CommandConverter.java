package command.converter;

import command.Command;
import command.commands.ArgumentCommand;
import command.commands.Commands;
import command.commands.CompositeCommand;
import command.commands.ExpressionCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;

public class CommandConverter {

	private static final Command.Parser DEFAULT_PARSER = Command.Parser.builder()
		.thenParser(ArgumentCommand.Parser::new)
		.thenParser(ExpressionCommand.Parser::new)
		.thenParser(CompositeCommand.Parser::new)
		.build();

	private final CommandSeparator separator;
	private final Command.Parser parser;
	private final HashMap<String, Command> commandsContainer = new HashMap<>();

	public CommandConverter(List<? extends Command> commands) {
		this(CommandSeparator.SPACE, DEFAULT_PARSER, commands);
	}

	public CommandConverter(CommandSeparator separator,
													Command.Parser parser,
													List<? extends Command> commands) {
		this.separator = separator;
		this.parser = parser;
		List.copyOf(commands).forEach(command -> commandsContainer.put(command.getName(), command));
	}

	public Command convert(String inputLine) {
		return doConvert(separate(inputLine));
	}


	private List<String> separate(String inputLine) {
		return separator.pattern().matcher(inputLine).results()
			.map(MatchResult::group)
			.toList();
	}

	private Command doConvert(List<String> commandParts) {
		if (commandParts.size() < 2) {
			return findByName(commandParts.get(0)).orElseThrow();
		}
		var parent = doConvert(commandParts.subList(0, commandParts.size() - 1));
		var commandPart = commandParts.get(commandParts.size() - 1);

		return parser.parse(parent, commandPart);
	}

	private Optional<Command> findByName(String name) {
		return Optional.ofNullable(commandsContainer.get(name));
	}


	public static CommandConverter.Builder builder() {
		return new Builder();
	}

	private static class Builder {

		private final List<Command> acceptedCommands = new ArrayList<>();
		private CommandSeparator separator;
		private Command.Parser parser;

		public Builder using(List<? extends Command> commands) {
			acceptedCommands.addAll(commands);
			return this;
		}

		public Builder separator(CommandSeparator separator) {
			this.separator = separator;
			return this;
		}

		public Builder parser(Command.Parser parser) {
			this.parser = parser;
			return this;
		}

		public CommandConverter build() {
			return new CommandConverter(separator, parser, acceptedCommands);
		}
	}


	public static void main(String[] args) {
		CommandConverter converter = CommandConverter.builder()
			.separator(CommandSeparator.SPACE)
			.parser(
				Command.Parser.builder()
					.thenParser(ArgumentCommand.Parser::new)
					.thenParser(CompositeCommand.Parser::new)
					.build()
			)
			.using(List.of(Command.simple("name", () -> {})))
			.using(List.of(Command.simple("other", () -> {})))
			.using(Commands.SYSTEM_COMMANDS)
			.build();

		CommandConverter converter1 = new CommandConverter(Commands.SYSTEM_COMMANDS);

		converter.convert("sys os").execute();
		converter.convert("sys echo -up \"Hello world\"").execute();
		converter.convert("sys echo -dwn \"HELLO WOlrD").execute();
		converter.convert("name").execute();
	}
}
