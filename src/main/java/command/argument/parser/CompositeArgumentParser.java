package command.argument.parser;

import java.util.Iterator;
import java.util.List;

class CompositeArgumentParser implements ArgumentParser<Object>, ArgumentParser.Resettable {

    private Iterator<ArgumentParser<?>> parserIterator;
    private List<ArgumentParser<?>> parsers;

    public CompositeArgumentParser(List<ArgumentParser<?>> parsers) {
        parserIterator = parsers.listIterator();
        this.parsers = parsers;
    }

    @Override
    public Object parse(String context) {
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

