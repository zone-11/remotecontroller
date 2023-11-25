package command.argument.parser;

import java.util.stream.Stream;

public interface ArgumentParser<T> {

    T parse(String context);

    default int getArgumentsNumber() {
        return 1;
    }

    @SuppressWarnings("unchecked")
    static ArgumentParser<Object> of(ArgumentParser<?>... parsers) {
        return new CompositeArgumentParser<>(
                Stream.of(parsers)
                        .map(parser -> (ArgumentParser<Object>)parser)
                        .toList()
        );
    }

    @SuppressWarnings("unchecked")
    private static <T> ArgumentParser<T> of2(ArgumentParser<? extends T>... parsers) {
        return new CompositeArgumentParser<>(
                Stream.of(parsers)
                        .map(parser -> (ArgumentParser<T>)parser)
                        .toList()
        );
    }
}