package command.parser;

import command.Command;

public class SimpleCommandParser extends CommandParser {

    @Override
    protected Command hookParse(String context) {
        return Command.forCommandName(context);
    }

}
