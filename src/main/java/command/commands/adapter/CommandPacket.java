package command.commands.adapter;

import command.Command;
import command.commands.AbstractSimpleCommand;

import java.util.Collection;

public class CommandPacket extends AbstractSimpleCommand {

    public CommandPacket(Collection<Command> collection) {
       super(
               collection.stream()
                .map(Command::getName)
                .reduce((str1, str2) -> str1 + " " + str2)
                .orElse("none"),
               () -> collection.forEach(Command::execute)

       );
    }
}
