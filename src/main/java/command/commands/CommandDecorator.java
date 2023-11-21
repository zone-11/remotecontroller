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
    public void setParent(Command parent) {
        command.setParent(parent);
    }

    @Override
    public Command getParent() {
        return command.getParent();
    }

    @Override
    public String toString() {
        return (getParent() != null ? getParent() + " " : "") + getName();
    }

}
