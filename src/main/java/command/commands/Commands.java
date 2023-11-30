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
                  ArgumentParser.of(
                    ArgumentParsers.flag("-up"),
                    ArgumentParsers.STRING
                  ), args -> System.out.println(args.get(1).toUpperCase())
                )
                .argAction(
                  ArgumentParser.of(
                    ArgumentParsers.flag("-dwn"),
                    ArgumentParsers.STRING
                  ), args -> System.out.println(args.get(1).toLowerCase())
                )
                .build()
            )
            .thenCommand(Command.simple("os", () -> {
                System.out.println(System.getProperty("os.name"));
            }))
            .build()
        );
    }
}