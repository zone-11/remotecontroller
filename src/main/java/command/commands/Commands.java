package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.ArgumentParsers;

public class Commands {

    private Commands() {}

    public static void init() {
      Command.add(
        new ArgumentCommand.Builder()
          .name("echo")
          .argAction(ArgumentParsers.STRING, args -> System.out.println(args.get(0)))
          .argAction(ArgumentParser.<Object>of(ArgumentParsers.STRING, ArgumentParsers.INTEGER), args -> {
            var str = (String)args.get(0);
            var repeatCount = (int)args.get(1);

            for (int i = 0; i < repeatCount; i ++) {
              System.out.println(str);
            }
          })
          .argAction(ArgumentParser.of(ArgumentParsers.flag("-up"), ArgumentParsers.STRING), args -> {
            System.out.println(args.get(1).toUpperCase());
          })
          .argAction(ArgumentParser.of(ArgumentParsers.flag("-dwn"), ArgumentParsers.STRING), args -> {
            System.out.println(args.get(1).toLowerCase());
          })
          .build()
      );

      Command.add(
        new CompositeCommand.Builder()
          .name("sys")
          .action(() -> System.out.println("shell: system commands"))
          .thenCommand("whoami", () -> {
            System.getProperties().entrySet().forEach(System.out::println);
          })
          .thenCommand("cd")
          .thenCommand("rm")
          .thenCommand("mkdir")
          .thenCommand("mv")
          .thenCommand("cp")
          .build()
      );

      Command.add(
        new ExpressionCommand("remote", command -> {
          System.out.println("\n--remote execution--");
          command.execute();
        })
      );
    }
}