package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.ArgumentParsers;
import command.argument.parser.Flag;

import java.util.List;

public final class Commands {

    public static final List<Command> SYSTEM_COMMANDS = sysCommands();

    private static List<Command> sysCommands() {
      final Flag<String> uppercaseFlag = new Flag<>("-up", String::toUpperCase);
      final Flag<String> lowercaseFlag = new Flag<>("-dwn", String::toLowerCase);
      final Flag<String> reverseFlag = new Flag<>("--reverse", str -> new StringBuilder(str).reverse().toString());
      final Flag<Integer> intFlag = new Flag<>("--some", integer -> integer + 5);

        return List.of(
          new CompositeCommand.Builder()
            .name("sys")
            .thenCommand(
              new ArgumentCommand.Builder()
                .name("echo")
                .argAction(
                  ArgumentParsers.withFlags(ArgumentParsers.STRING,
                    uppercaseFlag,
                    lowercaseFlag,
                    reverseFlag),
                  args -> System.out.println(args.get(0))
                )
                .argAction(
                  ArgumentParser.<Object>of(
                    ArgumentParsers.withFlags(ArgumentParsers.STRING,
                      uppercaseFlag,
                      lowercaseFlag,
                      reverseFlag),
                    ArgumentParsers.INTEGER
                  ), args -> {
                    var str = (String)args.get(0);
                    int repeatCount = (int)args.get(1);
                    for (int i = 0; i < repeatCount; i++) {
                      System.out.println(str);
                    }
                  }
                )
                .build()
            )
            .build()
        );
    }
}