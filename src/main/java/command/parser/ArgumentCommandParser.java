package command.parser;

import command.ArgumentRemoteCommand;
import command.Command;

public class ArgumentCommandParser extends CommandParser {

    @Override
    public Command hookParse(String context) {
        StringBuilder builder = new StringBuilder(context);
        String argument = context.split(" ")[-1];
        Command command = Command.forCommandName(builder.substring(0, context.length() -
                argument.length() + 1));

        if (command instanceof ArgumentRemoteCommand<?> argRemoteCommand) {
            argRemoteCommand.setArgument(argument);
            return argRemoteCommand;
        }
        return null;
    }

}
