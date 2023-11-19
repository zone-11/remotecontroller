package command.converter;

import command.Command;
import command.commands.ArgumentCommand;
import command.commands.Commands;
import command.commands.CompositeCommand;

import java.util.List;


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
        } else if (parent instanceof CompositeCommand compositeCommand) {
            return compositeCommand.getChildCommand(commandPart).orElseThrow();
        } else {
            throw new IllegalArgumentException("command " + commandPart + " does not exist");
        }
    }



    public static void main(String[] args) {
        Commands.init();
		CommandConverter converter = new DefaultCommandConverter();

        converter.parse("test your self   \"say hello to my little friends\" ").execute();
	}
}

