package command.argument.parser;

import java.io.File;

public class ArgumentParsers {

    public static final ArgumentParser<String> STRING = new StringArgumentParser();
    public static final ArgumentParser<Integer> INTEGER = new IntegerArgumentParser();
    public static final ArgumentParser<Boolean> BOOLEAN = new BooleanArgumentParser();

    private ArgumentParsers() {}

    @SafeVarargs
    public static <T> ArgumentParser<T> withFlags(ArgumentParser<T> parser, Flag<T>... flags) {
        FlagArgumentParser<T> flagParser = new FlagArgumentParser<>(parser, flags[0]);
        for (int i = 1; i < flags.length; i++) {
            flagParser = new FlagArgumentParser<>(flagParser, flags[i]);
        }
        return flagParser;
    }

    public static ArgumentParser<File> file(FileType fileType) {
        return new FilePathArgumentParser(fileType);
    }
}