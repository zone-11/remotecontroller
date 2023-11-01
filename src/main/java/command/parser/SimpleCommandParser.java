package command.parser;

import command.RemoteCommand;

public class SimpleCommandParser extends CommandParser {

    @Override
    protected RemoteCommand hookParse(String context) {
        return RemoteCommand.forCommandName(context);
    }

}
