package command.converter;

import command.commands.ArgumentCommand;
import command.commands.PrintCommand;
import command.commands.SimpleCompositeCommand;
import command.Command;

import java.util.List;
import java.util.regex.Pattern;


public class DefaultCommandConverter extends CommandConverter {

    Pattern commandPartPattern = Pattern.compile("(\".*\"|\\S+)\\s+");

    @Override
    public Command hookParse(List<String> commandParts) {
        Command parent = null;
		String commandPart = commandParts.get(commandParts.size() - 1);

		System.out.println(commandPart);

        if (commandParts.size() > 1) {
            parent = hookParse(commandParts.subList(0, commandParts.size() - 1));
        } else {
            return Command.findByName(commandPart).orElseThrow();
        }

        if (parent instanceof ArgumentCommand<?> argCommand) {
            argCommand.addArgument(commandPart);
            return argCommand;
        }
        return parent.getChildCommand(commandPart).orElseThrow();
    }



    public static void main(String[] args) {
		Command.add(new PrintCommand(1, new SimpleCompositeCommand()));

		CommandConverter converter = new DefaultCommandConverter();
		Command command = converter.parse("print \"Hello world\"");

		command.execute();
		System.out.println("COMPLETE");
	}
}

