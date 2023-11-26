package command.converter;

import command.Command;
import command.commands.adapter.CommandPacket;
import command.separator.CommandSeparator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public abstract class CommandConverter {

    public CommandPacket parse(String inputLine) {
		return new CommandPacket(createSeparator()
				.separate(inputLine).stream()
				.map(this::hookParse)
				.toList());
	}

	protected abstract Command hookParse(List<String> separateLine);

	protected CommandSeparator createSeparator() {
		Pattern commandPartPattern = Pattern.compile("\"[^\"]+\"|\\S+");
		return (inputLine) -> {
			List<String> list = commandPartPattern.matcher(inputLine).results()
							.map(MatchResult::group)
							.toList();
			return new ArrayList<>(List.of(list));
		};
	}

}
