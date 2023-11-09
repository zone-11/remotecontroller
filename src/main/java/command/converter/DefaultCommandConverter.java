package command.converter;

import command.commands.ArgumentCommand;
import command.commands.Print2Command;
import command.commands.PrintCommand;
import command.commands.SimpleCompositeCommand;
import command.Command;

import java.util.List;
import java.util.regex.Pattern;


public class DefaultCommandConverter extends CommandConverter {

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
		CommandConverter converter = new DefaultCommandConverter();
        Command print = new Print2Command(new SimpleCompositeCommand());

        Command.add(print);
        Command.add(new PrintCommand(new SimpleCompositeCommand()));
        converter.parse("print \"Hello world\" \"Say hello to my little friends\" ").execute();
        converter.parse("print2 \"--RAINBOW--\" 10").execute();
	}
}

