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
                .withArgument(Parsers.STRING_PARSER, 1, args -> {
                    System.out.println("Message: " + args.get(0));
                })
                .withArgument(ArgumentParser.of(Parsers.STRING_PARSER, Parsers.INTEGER_PARSER),
                            2, args -> {
                   var str = (String)args.get(0);
                   var repeatQ = (int)args.get(1);

                   for (int i = 0; i < repeatQ; i++) {
                       System.out.println("Message: " + str);
                   }
                })
                .withArgument(Parsers.BOOLEAN_PARSER, 1, args -> {
                    System.out.println("Boolean: " + args.get(0));
                })
                .build()
            )
            .thenCommand(new SimpleCommand("time", () -> {
                System.out.println("Time: " + Calendar.getInstance().getTime());
            }))
            .build()
       );
    }

}
