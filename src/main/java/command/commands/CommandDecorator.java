package command.commands;

import command.Command;

abstract class CommandDecorator extends AbstractSimpleCommand {

    protected final Command command;

    public CommandDecorator(Command command) {
        super(command.getName(), command::execute);
        this.command = command;
    }

    abstract static class Builder implements Command.Builder {

        protected Command command;

        public Builder(String name) {
            command = new SimpleCommand(name);
        }

        public Builder(String name, Runnable action) {
            command = new SimpleCommand(name, action);
        }

        @Override
        public Command build() {
           return command;
        }
    }
}
