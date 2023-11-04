package command;

import java.util.HashMap;

public interface Command {

   static HashMap<String, Command> commands = new HashMap<>();


   void execute();

   String getName();

   void addCommand(Command command);

   void removeCommand(Command command);

   boolean hasCommand(Command command);



   public static void add(Command command) {
      commands.put(command.getName(), command);
   }

   public static Command forCommandName(String name) {
      return commands.get(name);
   }

}