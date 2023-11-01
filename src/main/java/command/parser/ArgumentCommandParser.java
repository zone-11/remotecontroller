package command.parser;

import command.ArgumentRemoteCommand;
import command.RemoteCommand;

public class ArgumentCommandParser extends CommandParser {

    @Override
    public RemoteCommand hookParse(String context) {
        StringBuilder builder = new StringBuilder(context);
        String argument = context.split(" ")[-1];
        RemoteCommand command = RemoteCommand.forCommandName(builder.substring(0, context.length() -
                argument.length() + 1));

        if (command instanceof ArgumentRemoteCommand<?> argRemoteCommand) {
            argRemoteCommand.setArgument(argument);
            return argRemoteCommand;
        }
        return null;
    }

}
