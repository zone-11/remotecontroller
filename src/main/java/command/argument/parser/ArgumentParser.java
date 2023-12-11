package command.argument.parser;

import java.util.List;
import java.util.Optional;

public interface ArgumentParser<T> {

    Optional<List<T>> parse(List<String> args);

    @SafeVarargs
    static <T> ArgumentParser<T> of(AbstractArgumentParser<? extends T>... parsers) {
        return new CompositeArgumentParser<>(List.of(parsers));
    }
}