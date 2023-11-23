package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.Parsers;

import java.util.Calendar;

public class Commands {

    private Commands() {}

    public static void init() {
       Command.add(new SimpleCommand("hello"));
       Command.add(
            new CompositeCommand.Builder("print")
            .thenCommand(
                new ArgumentCommand.Builder("this")
                .withArgument(Parsers.STRING_PARSER, args -> {
                    System.out.println("Message: " + args.get(0));
                })
                .withArgument(ArgumentParser.of(Parsers.STRING_PARSER, Parsers.INTEGER_PARSER), args -> {
                   var str = (String)args.get(0);
                   var repeatQ = (int)args.get(1);

                   for (int i = 0; i < repeatQ; i++) {
                       System.out.println("Message: " + str);
                   }
                })
                .withArgument(Parsers.BOOLEAN_PARSER, args -> {
                    System.out.println("Boolean: " + args.get(0));
                })
                .withArgument(ArgumentParser.of(Parsers.flag("-m"), Parsers.STRING_PARSER), args -> {
                    System.out.println("Message: " + args.get(1));
                })
                .withArgument(ArgumentParser.of(Parsers.flag("-t"), Parsers.STRING_PARSER), args -> {
                    System.out.println("Title: " + args.get(1));
                })
                .withArgument(ArgumentParser.of(Parsers.flag("--title"), Parsers.STRING_PARSER), args -> {
                    System.out.println("Title: " + args.get(1));
                })
                .build()
            )
            .build()
       );
       Command.add(
            new ArgumentCommand.Builder("time", () -> {
                System.out.println("Current Time: " + Calendar.getInstance().getTime());
            })
            .withArgument(Parsers.flag("-s"), args -> {
                System.out.println("With flag " + args.get(0));
                System.out.println("Current Seconds: " + Calendar.getInstance()
                .getTime().getSeconds());
            })
            .withArgument(Parsers.flag("-h"), args -> {
                System.out.println("Current Hours: " + Calendar.getInstance()
                .getTime().getHours());
            })
            .build()
       );
       Command.add(new ExpressionCommand("remote", command -> {
           System.out.println("--REMOTE EXECUTION--");
           command.execute();
       }));
    }

}
