package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.ArgumentParsers;

import java.util.List;

public final class Commands {

    public static final List<Command> SYSTEM_COMMANDS = sysCommands();

    private static List<Command> sysCommands() {
        return List.of(
          new CompositeCommand.Builder()
            .name("sys")
            .action(() -> System.out.println("SYSTEM COMMANDS"))
            .thenCommand(
              new ArgumentCommand.Builder()
                .name("echo")
                .argAction(ArgumentParsers.STRING, args -> System.out.println(args.get(0)))
                .argAction(
                  ArgumentParsers.withFlag(
                    ArgumentParsers.STRING,
                    "-up",
                    String::toUpperCase
                  ), args -> System.out.println(args.get(0))
                )
                .argAction(
                  ArgumentParsers.withFlag(
                    ArgumentParsers.STRING,
                    "-dwn",
                    String::toLowerCase
                  ), args -> System.out.println(args.get(0))
                )
                .argAction(
                  ArgumentParser.<Object>of(
                    ArgumentParsers.STRING,
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
            .thenCommand(new Command.Simple("os", () -> {
                System.out.println(System.getProperty("os.name"));
            }))
            .build()
        );
    }
}