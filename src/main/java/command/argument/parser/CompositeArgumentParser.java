package command.argument.parser;

import java.util.Iterator;
import java.util.List;

class CompositeArgumentParser<T> implements ResettableArgumentParser<T> {

    private Iterator<ArgumentParser<T>> parserIterator;
    private List<ArgumentParser<T>> parsers;

    public CompositeArgumentParser(List<ArgumentParser<T>> parsers) {
        parserIterator = parsers.listIterator();
        this.parsers = parsers;
    }

    @Override
    public T parse(String context) {
        return parserIterator.hasNext() ? parserIterator.next().parse(context) : null;
    }

    @Override
    public void reset() {
       parserIterator = parsers.listIterator();
    }

    @Override
    public int getArgumentsNumber() {
       return parsers.size();
    }
}

