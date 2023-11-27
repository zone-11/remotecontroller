package command.argument.parser;

import java.util.Iterator;
import java.util.List;

class CompositeArgumentParser<T> implements ResettableArgumentParser<T> {

    private final List<ArgumentParser<? extends T>> parsers;
    private Iterator<ArgumentParser<? extends T>> parserIterator;

    public CompositeArgumentParser(List<ArgumentParser<? extends T>> parsers) {
        this.parsers = parsers;
        parserIterator = this.parsers.listIterator();
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

