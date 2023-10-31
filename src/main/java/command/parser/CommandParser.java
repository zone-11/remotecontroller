package command.parser;

import command.RemoteCommand;

public interface CommandParser {

    public RemoteCommand parse(String commandText);

}