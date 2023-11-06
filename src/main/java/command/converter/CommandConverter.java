package command.converter;

import command.Command;
import command.separater.CommandSeparater;

public abstract class CommandConverter {

    public Command parse(String inputLine) {
		return null;
	}

	protected abstract Command hookParse(String[] separateLine);

	protected abstract CommandSeparater createSeparater();

}
