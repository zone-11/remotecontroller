package command.commands;

import command.Command;

abstract class CommandDecorator extends AbstractSimpleCommand {

    protected final Command command;

    public CommandDecorator(Command command) {
        super(command.getName(), command::execute);
        this.command = command;
    }
}
