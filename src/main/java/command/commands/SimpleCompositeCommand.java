package command.commands;

public class SimpleCompositeCommand extends AbstractCompositeCommand {

	@Override
	public void execute() {
		childs.values().forEach(command -> command.execute());
	}

	@Override
	public String getName() {
		return "simple_composite";
	}
	
}
