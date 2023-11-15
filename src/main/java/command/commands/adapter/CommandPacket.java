package command.commands.adapter;

import command.Command;

import java.util.Collection;

public class CommandPacket implements Command {

    private Collection<Command> collection;

    public CommandPacket(Collection<Command> collection) {
        this.collection = collection;
    }

    @Override
    public void execute() {
       collection.forEach(Command::execute);
    }

    @Override
    public String getName() {
        return collection.stream()
                .map(Command::getName)
                .reduce((str1, str2) -> str1 + " " + str2)
                .orElse("none");
    }
}
