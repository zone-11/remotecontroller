package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.ArgumentParsers;
import command.argument.parser.Flag;

import java.util.Calendar;
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
            .thenCommand("whoami", () -> {
              System.out.println(System.getProperty("user.name"));
            })
            .thenCommand("os", () -> {
              System.out.println(System.getProperty("os.name"));
            })
            .thenCommand("time", () -> {
              System.out.println(Calendar.getInstance().getTime());
            })
            .thenCommand(
              new ParameterCommand.Builder()
                .name("testparam")
                .action(() -> System.out.println("TEST COMMAND"))
                .parameter("str", ArgumentParsers.STRING, () -> "string")
                .parameter("int", ArgumentParsers.INTEGER, () -> 100)
                .paramAction(map -> {
                  System.out.println("STR: " + map.get("str"));
                  System.out.println("INT: " + map.get("int"));
                })
                .build()
            )
            .build()
        );
    }
}