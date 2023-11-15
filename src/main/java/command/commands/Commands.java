package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.Parsers;
import command.builders.ArgumentCommandBuilder;

public class Commands {

    public static void init() {
        Command.add(
                new ArgumentCommandBuilder("print")
                        .then(
                                new ArgumentCommandBuilder("this", () -> System.out.println("Message: -OK-"))
                                        .withArgument(Parsers.STRING_PARSER, 1, argsList ->{
                                            System.out.println("Message: " + argsList.get(0));
                                        })
                                        .withArgument(ArgumentParser.of(Parsers.STRING_PARSER, Parsers.INTEGER_PARSER),
                                                2, argsList -> {
                                                    String text = (String)argsList.get(0);
                                                    int repeatQuantity = (int)argsList.get(1);

                                                    for(int i = 0; i < repeatQuantity; i++) {
                                                        System.out.println("Message: " + text);
                                                    }
                                                })
                                        .withArgument(Parsers.INTEGER_PARSER, 1, argsList ->{
                                            System.out.println("Integer: " + (int)argsList.get(0));
                                        })
                                        .withArgument(Parsers.BOOLEAN_PARSER, 1, argsList -> {
                                            System.out.println("Boolean: " + argsList.get(0));
                                        })
                                        .build()
                        )
                        .build()
        );
    }

}
