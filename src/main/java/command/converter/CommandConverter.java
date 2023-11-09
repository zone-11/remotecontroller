package command.converter;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

import command.Command;
import command.commands.SimpleCompositeCommand;
import command.separater.CommandSeparater;

public abstract class CommandConverter {

    public Command parse(String inputLine) {
		SimpleCompositeCommand composite = new SimpleCompositeCommand();

		createSeparater().separate(inputLine).forEach(command -> {
			composite.addCommand(hookParse(command));
		});


		return composite;
	}

	protected abstract Command hookParse(List<String> separateLine);

	protected  CommandSeparater createSeparater() {
		Pattern commandPartPattern = Pattern.compile("\".*\"|\\S+");
		return (inputLine) -> {
			List<String> list = commandPartPattern.matcher(inputLine).results()
							.map(result -> result.group())
							.toList();
			return new ArrayList<List<String>>(List.of(list));
		};
	}

}
