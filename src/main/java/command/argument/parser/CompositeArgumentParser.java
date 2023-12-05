package command.argument.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

class CompositeArgumentParser<T> implements ResettableArgumentParser<T> {

    private final List<ArgumentParser<? extends T>> parsers;
    private final int inputArgsCount;
    private final int outputArgsCount;

    private ListIterator<ArgumentParser<? extends T>> parserIterator;

    public CompositeArgumentParser(List<ArgumentParser<? extends T>> parsers) {
        inputArgsCount = parsers.stream()
          .mapToInt(ArgumentParser::inputArgsCount)
          .sum();
        outputArgsCount = parsers.stream()
          .mapToInt(ArgumentParser::outputArgsCount)
          .sum();

        List<ArgumentParser<? extends T>> parsersMulti = new ArrayList<>();
        for (ArgumentParser<? extends T> parser : parsers) {
            for (int i = 0; i < parser.inputArgsCount(); i++) {
                parsersMulti.add(parser);
            }
        }
        this.parsers = parsersMulti;
        parserIterator = parsersMulti.listIterator();
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
    public int inputArgsCount() {
        return inputArgsCount;
    }

    @Override
    public int outputArgsCount() {
        return outputArgsCount;
    }
}

