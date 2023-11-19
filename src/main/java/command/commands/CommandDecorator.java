package command.commands;

import command.Command;

abstract class CommandDecorator implements Command {

    protected Command command;
    private Command parent;

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
       this.parent = parent;
       command.setParent(parent);

    }

    @Override
    public Command getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return (parent != null ? parent + " " : "") + getName();
    }

}
