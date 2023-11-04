package command.commands;

import command.Command;

import java.util.*;

public abstract class AbstractCompositeCommand implements Command {

    protected HashMap<String, Command> childs = new HashMap<>();

    @Override
    public void addCommand(Command command) {
        childs.put(command.getName(), command);
    }

    @Override
    public void removeCommand(Command command) {
        childs.remove(command.getName());
    }

    @Override
    public Optional<Command> getChildCommand(String commandName) {
        return Optional.ofNullable(childs.get(commandName));
    }
}
