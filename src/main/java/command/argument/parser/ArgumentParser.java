package command.argument.parser;

import java.util.List;

public interface ArgumentParser<T> {

    T parse(String context);

    default int inputArgsCount() {
        return 1;
    }

    default int outputArgsCount() {
        return inputArgsCount();
    }



    @SafeVarargs
    static <T> ArgumentParser<T> of(ArgumentParser<? extends T>... parsers) {
        return new CompositeArgumentParser<>(List.of(parsers));
    }
}