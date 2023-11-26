package command.commands.adapter;

import command.Command;
import command.commands.AbstractSimpleCommand;

import java.util.Collection;
import java.util.Iterator;

public class CommandPacket extends AbstractSimpleCommand implements Iterable<Command> {

    private final Iterator<Command> commandIterator;

    public CommandPacket(Collection<Command> collection) {
       super(
               collection.stream()
                .map(Command::getName)
                .reduce((str1, str2) -> str1 + " " + str2)
                .orElse("none"),
               () -> collection.forEach(Command::execute)

       );
       commandIterator = collection.iterator();
    }

    @Override
    public Iterator<Command> iterator() {
       return commandIterator;
    }
}
