package command.argument.parser;

import java.util.List;
import java.util.Optional;

public interface ArgParser<T> {

    Optional<List<T>> parse(List<String> args);

    @SafeVarargs
    static <T> ArgParser<T> of(AbstractArgParser<? extends T>... parsers) {
        return new CompositeArgParser<>(List.of(parsers));
    }
}