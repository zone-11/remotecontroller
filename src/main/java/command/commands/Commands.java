package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.FileType;
import command.argument.parser.ArgumentParsers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Commands {

    private Commands() {}

    public static void init() {
        Command.add(
           new ArgumentCommand.Builder("cp")
               .arguments(
                   ArgumentParser.of(
                       ArgumentParsers.file(FileType.FILE),
                       ArgumentParsers.file(FileType.DIRECTORY)
                   ),
                   args -> {
                       var file = (File)args.get(0);
                       var dest = (File)args.get(1);
                       System.out.println("Copying file: " + file.getAbsolutePath());
                       System.out.println("To: " + dest.getAbsolutePath());

                       try {
                           File copy = new File(dest, file.getName());
                           if (copy.createNewFile()) {
                               Files.copy(file.toPath(), new FileOutputStream(copy));
                           }
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                   }
               )
               .build()
        );
        Command.add(
            new ArgumentCommand.Builder("mv")
                .arguments(
                    ArgumentParser.of(
                        ArgumentParsers.file(FileType.FILE),
                        ArgumentParsers.file(FileType.DIRECTORY)
                    ),
                    args -> {
                        var file = args.get(0);
                        var dest = args.get(1);

                        if (file.renameTo(dest)) {
                            System.out.println("Move " + file.getAbsolutePath() + " to " +
                                dest.getAbsolutePath());
                        }
                    }
                )
                .build()
        );
        Command.add(
            new ArgumentCommand.Builder("echo")
                .arguments(ArgumentParsers.STRING_PARSER, args -> System.out.println(args.get(0)))
                .arguments(
                    ArgumentParser.of(
                        ArgumentParsers.flag("-up"),
                        ArgumentParsers.STRING_PARSER
                    ),
                    args -> {
                        String str = args.get(1);
                        System.out.println(str.toUpperCase());
                    }
                )
                .arguments(
                    ArgumentParser.of(
                        ArgumentParsers.flag("-dwn"),
                        ArgumentParsers.STRING_PARSER
                    ),
                    args -> {
                        System.out.println(args.get(1).toLowerCase());
                    }
                )
                .arguments(
                    ArgumentParser.of(
                        ArgumentParsers.flag("--reverse"),
                        ArgumentParsers.STRING_PARSER
                    ),
                    args -> {
                        var builder = new StringBuilder(args.get(1));
                        System.out.println(builder.reverse());
                    }
                )
                .build()
        );
        Command.add(new ExpressionCommand("remote", command -> {
            System.out.println("\n--REMOTE EXECUTION--");
            command.execute();
        }));
        Command.add(new SimpleCommand("os", () -> {
            System.getProperties().entrySet().forEach(System.out::println);
        }));
    }
}