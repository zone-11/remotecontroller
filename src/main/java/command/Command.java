package command;

import command.commands.CompositeCommand;
import command.commands.SimpleCommand;

import java.util.HashMap;
import java.util.Optional;

public interface Command {

   static HashMap<String, Command> commands = new HashMap<>();

   void execute();

   String getName();

   void setParent(Command command);

   Command getParent();

   abstract class Builder<T extends Builder<T>> {

      protected Command command;

      public Builder(String commandName, Runnable action) {
         command = new SimpleCommand(commandName, action);
      }

      public Builder(String commandName) {
         command = new SimpleCommand(commandName);
      }

      public Builder<T> then(Command childCommand) {
         this.command = new CompositeCommand(command, childCommand);
         return self();
      }

      public Builder<T> then(String commandName) {
         this.command = new CompositeCommand(command, new SimpleCommand(commandName));
         return self();
      }

      public Builder<T> then(String commandName, Runnable action) {
         this.command = new CompositeCommand(command, new SimpleCommand(commandName, action));
         return self();
      }

      public Command build() {
         return command;
      }

      protected abstract Builder<T> self();

   }


   static void add(Command command) {
      commands.put(command.getName(), command); 
   }

   static Optional<Command> findByName(String name) {
      return Optional.ofNullable(commands.get(name));
   }

}
