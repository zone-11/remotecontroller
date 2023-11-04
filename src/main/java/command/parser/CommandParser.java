package command.parser;

import command.Command;

import java.util.Optional;

public abstract class CommandParser implements Parser<Command> {

    private CommandParser next;

    @Override
    public Command parse(String context) {
        return Optional.ofNullable(hookParse(context))
                .orElseGet(() -> next.parse(context));
    }

    protected abstract Command hookParse(String context);

}