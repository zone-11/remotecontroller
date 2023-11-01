package command.parser.argument;

import command.parser.Parser;

public interface ArgumentParser<T> extends Parser<T> {
    public boolean canParse(String context);

}