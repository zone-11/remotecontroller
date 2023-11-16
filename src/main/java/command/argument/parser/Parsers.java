package command.argument.parser;

import command.argument.parser.impl.BooleanArgumentParser;
import command.argument.parser.impl.IntegerArgumentParser;
import command.argument.parser.impl.StringArgumentParser;

public class Parsers {

    public static final ArgumentParser<String> STRING_PARSER = new StringArgumentParser();
    public static final ArgumentParser<Integer> INTEGER_PARSER = new IntegerArgumentParser();
    public static final ArgumentParser<Boolean> BOOLEAN_PARSER = new BooleanArgumentParser();

    private Parsers() {}

}
