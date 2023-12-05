package command.converter;

import command.Command;
import command.commands.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;

public class CommandConverter {

	private final CommandSeparator separator;
	private final HashMap<String, Command> commandsContainer = new HashMap<>();

	public CommandConverter(List<? extends Command> commands) {
		this(CommandSeparator.SPACE, commands);
	}

	public CommandConverter(CommandSeparator separator,
													List<? extends Command> commands) {
		this.separator = separator;
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

		return parent.parser().apply(commandPart);
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

		public Builder using(List<? extends Command> commands) {
			acceptedCommands.addAll(commands);
			return this;
		}

		public Builder separator(CommandSeparator separator) {
			this.separator = separator;
			return this;
		}

		public CommandConverter build() {
			return new CommandConverter(separator, acceptedCommands);
		}
	}


	public static void main(String[] args) {
		CommandConverter converter = CommandConverter.builder()
			.separator(CommandSeparator.SPACE)
			.using(List.of(new Command.Simple("name", () -> {})))
			.using(List.of(new Command.Simple("another", () -> {})))
			.using(Commands.SYSTEM_COMMANDS)
			.build();

		final CommandConverter converter1 = new CommandConverter(Commands.SYSTEM_COMMANDS);

		converter.convert("sys echo -up \"Hello world\" --some 5").execute();
		converter.convert("sys echo \"Hello world\" 5").execute();

		converter.convert("sys os").execute();
		converter.convert("sys echo -dwn \"HELLO WOlrD\"").execute();
		converter.convert("sys echo --reverse \"HELLO WOlrD\"").execute();
		converter.convert("sys echo -up \"hello world\"").execute();
	}
}
