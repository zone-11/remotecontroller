package command.commands;

import command.Command;

public abstract class AbstractSimpleCommand implements Command {

    private final String commandName;
    private final Runnable action;

    public AbstractSimpleCommand(String commandName, Runnable action) {
        this.commandName = commandName;
        this.action = action;
    }

    @Override
    public void execute() {
       action.run();
    }

    @Override
    public String getName() {
        return commandName;
    }

    @Override
    public String toString() {
        return commandName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractSimpleCommand command)) {
            return false;
        }
        return command.commandName.equals(commandName);
    }

    @Override
    public int hashCode() {
        return 31 * commandName.hashCode();
    }

    public static class Simple extends AbstractSimpleCommand {
        public Simple(String commandName, Runnable action) {
            super(commandName, action);
        }

        @Override
        public Parser<?> parser() {
            return null;
        }
    }
}
