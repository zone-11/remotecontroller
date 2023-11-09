package command.commands;

import command.Command;

public class SimpleCompositeCommand extends AbstractCompositeCommand {

	@Override
	public void execute() {
		childs.values().forEach(Command::execute);
	}

	@Override
	public String getName() {
		return "simple_composite";
	}
	
}
