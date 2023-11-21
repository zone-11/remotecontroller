package command.argument.parser;

public class Parsers {

    public static final ArgumentParser<String> STRING_PARSER = new StringArgumentParser();
    public static final ArgumentParser<Integer> INTEGER_PARSER = new IntegerArgumentParser();
    public static final ArgumentParser<Boolean> BOOLEAN_PARSER = new BooleanArgumentParser();

    private Parsers() {}

    public static ArgumentParser<String> flag(String flag) {
        return new FlagArgumentParser(flag);
    }

}
