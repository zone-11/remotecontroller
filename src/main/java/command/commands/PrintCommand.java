package command.commands;

import command.Command;
import command.parser.ArgumentParser;
import command.parser.argument.StringArgumentParser;

public class PrintCommand extends ArgumentCommand<String> {

	public PrintCommand(int argsQuantity, Command command) {
		super(argsQuantity, command);
	}

	@Override
	public void execute() {
		arguments.forEach(System.out::println);
	}

	@Override
	public ArgumentParser<String> getArgumentParser() {
		return new StringArgumentParser();
	}

	@Override
	public String getName() {
		return "print";
	}

}
