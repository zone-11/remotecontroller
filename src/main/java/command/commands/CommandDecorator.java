package command.commands;

import command.Command;

abstract class CommandDecorator<T extends Command> extends AbstractSimpleCommand {

    protected final T command;

    public CommandDecorator(T command) {
        super(command.getName(), command::execute);
        this.command = command;
    }
}
