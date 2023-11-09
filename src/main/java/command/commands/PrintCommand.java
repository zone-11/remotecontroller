package command.commands;

import command.Command;
import command.argument.parser.StringArgumentParser;

public class PrintCommand extends ArgumentCommand<String> {

	public PrintCommand(Command command) {
		super(2, command, new StringArgumentParser());
	}

	@Override
	public void execute() {
		arguments.forEach(msg -> {
			System.out.println("Message: " + msg);
		});
	}

	@Override
	public String getName() {
		return "print";
	}

}

