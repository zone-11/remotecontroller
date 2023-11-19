package command.argument.parser;

import command.argument.parser.impl.CompositeArgumentParser;

import java.util.List;

public interface ArgumentParser<T> {
    T parse(String context);


    interface Resettable {

        void reset();
    }

    static ArgumentParser<?> of(ArgumentParser<?>... parsers) {
        return new CompositeArgumentParser(List.of(parsers));
    }

}



