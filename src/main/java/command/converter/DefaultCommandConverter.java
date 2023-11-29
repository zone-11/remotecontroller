package command.converter;

import command.Command;
import command.commands.ArgumentCommand;
import command.commands.CompositeCommand;
import command.commands.ExpressionCommand;

import java.util.List;


public class DefaultCommandConverter extends CommandConverter {

    private static final Command.Parser COMMAND_PARSER = Command.Parser
        .builder()
        .thenParser(ArgumentCommand.Parser::new)
        .thenParser(ExpressionCommand.Parser::new)
        .thenParser(CompositeCommand.Parser::new)
        .build();

    public DefaultCommandConverter(List<? extends Command> commands) {
        super(commands);
    }

    @Override
    public Command hookConvert(List<String> commandParts) {
        if (commandParts.size() < 2) {
            return findByName(commandParts.get(0)).orElseThrow();
        }
        var parent = hookConvert(commandParts.subList(0, commandParts.size() - 1));
        var commandPart = commandParts.get(commandParts.size() - 1);

        return COMMAND_PARSER.parse(parent, commandPart);
    }
}

