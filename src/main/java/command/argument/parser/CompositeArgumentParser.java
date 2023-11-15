package command.argument.parser;

import java.util.Iterator;
import java.util.List;

public class CompositeArgumentParser implements ArgumentParser<Object> {

    private Iterator<ArgumentParser<?>> parserIterator;
    private List<ArgumentParser<?>> parsers;

    public CompositeArgumentParser(List<ArgumentParser<?>> parsers) {
        parserIterator = parsers.listIterator();
        this.parsers = parsers;
    }

    @Override
    public Object parse(String context) {
        if (!parserIterator.hasNext()) {
            parserIterator = parsers.listIterator();
        }
        return parserIterator.next().parse(context);
    }

}

