package command.argument.parser;

import java.io.File;
import java.util.Set;

public class ArgParsers {

    public static final AbstractSimpleArgParser<String> STRING = new StringArgParser();
    public static final AbstractSimpleArgParser<Integer> INTEGER = new IntegerArgParser();
    public static final AbstractSimpleArgParser<Boolean> BOOLEAN =
      AbstractSimpleArgParser.from(str -> str.equals("true")
        ? Boolean.TRUE
        : (str.equals("false") ? Boolean.FALSE : null));
    public static final AbstractSimpleArgParser<File> DIRECTORY =
      new FilePathArgParser(FilePathArgParser.FileType.DIRECTORY);
    public static final AbstractSimpleArgParser<File> FILE =
      new FilePathArgParser(FilePathArgParser.FileType.FILE);

    private ArgParsers() {}

    @SafeVarargs
    public static <T> AbstractArgParser<T> withFlags(AbstractSimpleArgParser<T> parser,
                                                     Flag<T>... flags) {
        return new FlagArgParser<>(parser, Set.of(flags));
    }
}