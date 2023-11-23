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

   abstract class Parser {

      protected Parser nextConverter;

      public Parser() {}

      public Parser(Parser nextConverter) {
         this.nextConverter = nextConverter;
      }

      public Command convert(Command command, String str) {
         if (command == null) {
            return Command.findByName(str).orElseThrow();
         }

         var toReturn = hookConvert(command, str);
         if (toReturn == null && nextConverter != null) {
            toReturn = nextConverter.convert(command, str);
         }
         return toReturn;
      }

      protected abstract Command hookConvert(Command command, String str);



      public static Builder builder() {
         return new Builder();
      }

      public static class Builder {

         private Parser converter;

         public Builder thenParser(Function<Parser, Parser> func) {
            converter = func.apply(converter);
            return this;
         }

         public Parser build() {
            return converter;
         }
      }
   }



}