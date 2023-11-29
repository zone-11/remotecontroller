package command.converter;

import command.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;

public abstract class CommandConverter {

	private final CommandSeparator separator;
	private final HashMap<String, Command> commandsContainer = new HashMap<>();

	public CommandConverter(List<? extends Command> commands) {
		this(CommandSeparator.SPACE, commands);
	}

	public CommandConverter(CommandSeparator separator, List<? extends Command> commands) {
		this.separator = separator;
		List.copyOf(commands).forEach(command -> commandsContainer.put(command.getName(), command));
	}

	public Command convert(String inputLine) {
		return hookConvert(separate(inputLine));
	}

	protected Optional<Command> findByName(String name) {
		return Optional.ofNullable(commandsContainer.get(name));
	}

	private List<String> separate(String inputLine) {
		return separator.pattern().matcher(inputLine).results()
			.map(MatchResult::group)
			.toList();
	}

	protected abstract Command hookConvert(List<String> separateLine);
}
