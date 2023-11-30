package command;

import java.util.function.Function;

//TODO: think about the return value in commands
public interface Command {

   void execute();

   String getName();



   static Command simple(String commandName, Runnable commandAction) {
      return new Command() {

         private final String name = commandName;
         private final Runnable action = commandAction;

         @Override
         public void execute() {
            action.run();
         }

         @Override
         public String getName() {
            return name;
         }
      };
   }


   abstract class Builder<T extends Builder<T>> {

      protected String name;
      protected Runnable action = () -> {};

      public final T name(String name) {
         this.name = name;
         return self();
      }

      public final T action(Runnable action) {
         this.action = action;
         return self();
      }

      public final Command build() {
         if (name != null) {
            return hookBuild(name, action);
         }
         throw new IllegalArgumentException("Command must have the name");
      }

      protected abstract Command hookBuild(String name, Runnable action);

      protected abstract T self();
   }

   abstract class Parser {

      protected Parser nextConverter;

      public Parser(Parser nextConverter) {
         this.nextConverter = nextConverter;
      }

      public Command parse(Command command, String str) {
         var toReturn = hookParse(command, str);

         if (toReturn == null && nextConverter != null) {
            toReturn = nextConverter.parse(command, str);
         }
         if (toReturn == null) {
            throw new IllegalArgumentException("command parser not found");
         }
         return toReturn;
      }

      protected abstract Command hookParse(Command command, String str);



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