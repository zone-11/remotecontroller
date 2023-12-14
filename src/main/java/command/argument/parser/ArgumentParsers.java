package command.argument.parser;

import java.io.File;
import java.util.Set;

public class ArgumentParsers {

    public static final AbstractSimpleArgumentParser<String> STRING = new StringArgumentParser();
    public static final AbstractSimpleArgumentParser<Integer> INTEGER = new IntegerArgumentParser();
    public static final AbstractSimpleArgumentParser<Boolean> BOOLEAN =
      AbstractSimpleArgumentParser.from(str -> str.equals("true")
        ? Boolean.TRUE
        : (str.equals("false") ? Boolean.FALSE : null));
    public static final AbstractSimpleArgumentParser<File> DIRECTORY =
      new FilePathArgumentParser(FilePathArgumentParser.FileType.DIRECTORY);
    public static final AbstractSimpleArgumentParser<File> FILE =
      new FilePathArgumentParser(FilePathArgumentParser.FileType.FILE);

    private ArgumentParsers() {}

    @SafeVarargs
    public static <T> AbstractArgumentParser<T> withFlags(AbstractSimpleArgumentParser<T> parser,
                                                          Flag<T>... flags) {
        return new FlagArgumentParser<>(parser, Set.of(flags));
    }
}