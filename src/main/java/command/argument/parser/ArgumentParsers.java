package command.argument.parser;

import java.io.File;

public class ArgumentParsers {

    public static final ArgumentParser<String> STRING = new StringArgumentParser();
    public static final ArgumentParser<Integer> INTEGER = new IntegerArgumentParser();
    public static final ArgumentParser<Boolean> BOOLEAN = new BooleanArgumentParser();

    private ArgumentParsers() {}

    public static ArgumentParser<String> flag(String flag) {
        return new FlagArgumentParser(flag);
    }

    public static ArgumentParser<File> file(FileType fileType) {
        return new FilePathArgumentParser(fileType);
    }
}
