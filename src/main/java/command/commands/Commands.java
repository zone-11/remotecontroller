package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.FileType;
import command.argument.parser.Parsers;

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
                       Parsers.file(FileType.FILE),
                       Parsers.file(FileType.DIRECTORY)
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
    }
}
