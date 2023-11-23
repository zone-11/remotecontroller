package command.commands;

import command.Command;

abstract class CommandDecorator implements Command {

    protected final Command command;

    public CommandDecorator(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
       command.execute();
    }

    @Override
    public String getName() {
        return command.getName();
    }


    @Override
    public String toString() {
        return command.toString();
    }
}
