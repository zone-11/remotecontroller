package command.argument.parser;

import java.util.Iterator;
import java.util.List;

public class CompositeArgumentParser implements ArgumentParser<Object> {

    private Iterator<ArgumentParser<?>> parserIterator;

    public CompositeArgumentParser(List<ArgumentParser<?>> parsers) {
        parserIterator = parsers.iterator();
    }

    @Override
    public Object parse(String context) {
        if (parserIterator.hasNext()) {
            ArgumentParser<?> nextParser = parserIterator.next();
            return nextParser.parse(context);
        }
        throw new IllegalArgumentException("argument: " + context + " does not exist");
    }

}

