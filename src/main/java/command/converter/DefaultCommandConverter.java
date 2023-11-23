package command.converter;

import command.Command;
import command.commands.ArgumentCommand;
import command.commands.Commands;
import command.commands.CompositeCommand;

import java.util.List;


public class DefaultCommandConverter extends CommandConverter {

    private static final Command.Parser COMMAND_PARSER = Command.Parser
    .builder()
    .thenParser(ArgumentCommand.Parser::new)
    .thenParser(CompositeCommand.Parser::new)
    .build();

    @Override
    public Command hookParse(List<String> commandParts) {
        if (commandParts.size() < 2) {
            return COMMAND_PARSER.convert(null, commandParts.get(0));
        }
        var parent = hookParse(commandParts.subList(0, commandParts.size() - 1));
        var commandPart = commandParts.get(commandParts.size() - 1);

        return COMMAND_PARSER.convert(parent, commandPart);
    }



    public static void main(String[] args) {
        Commands.init();
		CommandConverter converter = new DefaultCommandConverter();

        converter.parse("print this \"Hello world\"").execute();
        converter.parse("print this \"Goodbye world\" 10").execute();
        converter.parse("print this true").execute();
        converter.parse("print this -m \"Hello from Africa\"").execute();
        converter.parse("print this -t \"Out of Africa\"").execute();
        converter.parse("print this --title \"Out of Africa\"").execute();

        converter.parse("time").execute();
        converter.parse("time -s").execute();
        converter.parse("time -h").execute();
    }
}

