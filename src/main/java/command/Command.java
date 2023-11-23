package command;

import command.commands.SimpleCommand;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

public interface Command {

   static HashMap<String, Command> commands = new HashMap<>();

   void execute();

   String getName();



   static void add(Command command) {
      commands.put(command.getName(), command);
   }

   static Optional<Command> findByName(String name) {
      return Optional.ofNullable(commands.get(name));
   }


   abstract class Builder {

      protected Command command;

      public Builder(String commandName) {
         command = new SimpleCommand(commandName);
      }

      public Builder(String commandName, Runnable action) {
         command = new SimpleCommand(commandName, action);
      }

      public Command build() {
         return command;
      }

   }


   static void add(Command command) {
      commands.put(command.getName(), command); 
   }

   static Optional<Command> findByName(String name) {
      return Optional.ofNullable(commands.get(name));
   }

}
