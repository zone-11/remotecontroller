package command.argument.parser;

import java.io.File;
import java.util.function.Function;

public class ArgumentParsers {

    public static final ArgumentParser<String> STRING = new StringArgumentParser();
    public static final ArgumentParser<Integer> INTEGER = new IntegerArgumentParser();
    public static final ArgumentParser<Boolean> BOOLEAN = new BooleanArgumentParser();

    private ArgumentParsers() {}

    public static <T> ArgumentParser<T> withFlag(ArgumentParser<T> parser,
                                                  String flag,
                                                  Function<T, T> flagAction) {
        return new FlagArgumentParser<>(parser, flag, flagAction);
    }

    public static ArgumentParser<File> file(FileType fileType) {
        return new FilePathArgumentParser(fileType);
    }
}