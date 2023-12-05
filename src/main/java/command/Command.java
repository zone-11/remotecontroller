package command;

import java.util.function.Function;

//TODO: think about the return value in commands
public interface Command {

   void execute();
   String getName();
   Function<String, ? extends Command> parser();


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

   record Simple(String name, Runnable action) implements Command  {

      @Override
      public Function<String, Simple> parser() {
         return context -> {
            throw new IllegalArgumentException("the simple command doesn't take any arguments");
         };
      }

      public Simple {
         if (name == null || action == null) {
            throw new IllegalArgumentException("illegal command name");
         }
      }

      @Override
      public void execute() {
         action.run();
      }

      @Override
      public String getName() {
         return name;
      }

      @Override
      public String toString() {
         return name;
      }
   }
}