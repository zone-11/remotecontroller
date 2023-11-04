package command;

import java.util.HashMap;
import java.util.Optional;

public interface Command {

   static HashMap<String, Command> commands = new HashMap<>();


   void execute();

   String getName();

   void addCommand(Command command);

   void removeCommand(Command command);

   Optional<Command> getChildCommand(String commandName);



   public static void add(Command command) {
      commands.put(command.getName(), command);
   }

   public static Optional<Command> findByName(String name) {
      return Optional.ofNullable(commands.get(name));
   }

}