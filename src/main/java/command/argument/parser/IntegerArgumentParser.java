package command.argument.parser;

public class IntegerArgumentParser implements ArgumentParser<Integer> {

    @Override
    public Integer parse(String context) {
        return Integer.parseInt(context);
    }

}
