package command.parser;

import command.RemoteCommand;

import java.util.Optional;

public abstract class CommandParser implements Parser<RemoteCommand> {

    private CommandParser next;

    @Override
    public RemoteCommand parse(String context) {
        return Optional.ofNullable(hookParse(context))
                .orElseGet(() -> next.parse(context));
    }

    protected abstract RemoteCommand hookParse(String context);

}