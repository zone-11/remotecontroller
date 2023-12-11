package command.argument.parser;

import java.io.File;

public class ArgumentParsers {

    public static final AbstractArgumentParser<String> STRING = new StringArgumentParser();
    public static final AbstractArgumentParser<Integer> INTEGER = new IntegerArgumentParser();
    public static final AbstractArgumentParser<Boolean> BOOLEAN = new BooleanArgumentParser();
    public static final AbstractArgumentParser<File> DIRECTORY =
      new FilePathArgumentParser(FilePathArgumentParser.FileType.DIRECTORY);
    public static final AbstractArgumentParser<File> FILE =
      new FilePathArgumentParser(FilePathArgumentParser.FileType.FILE);

    private ArgumentParsers() {}

    @SafeVarargs
    public static <T> AbstractArgumentParser<T> withFlags(ArgumentParser<T> parser, Flag<T>... flags) {
        FlagArgumentParser<T> flagParser = new FlagArgumentParser<>(parser, flags[0]);
        for (int i = 1; i < flags.length; i++) {
            flagParser = new FlagArgumentParser<>(flagParser, flags[i]);
        }
        return flagParser;
    }
}