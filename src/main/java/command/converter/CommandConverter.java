package command.converter;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import command.Command;
import command.commands.SimpleCompositeCommand;
import command.separator.CommandSeparator;

public abstract class CommandConverter {

    public Command parse(String inputLine) {
		SimpleCompositeCommand composite = new SimpleCompositeCommand();

		createSeparator().separate(inputLine).forEach(command -> {
			composite.addCommand(hookParse(command));
		});


		return composite;
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
