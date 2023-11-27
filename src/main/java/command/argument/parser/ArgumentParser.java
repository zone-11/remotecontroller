package command.argument.parser;

import java.util.List;

public interface ArgumentParser<T> {

    T parse(String context);

    default int getArgumentsNumber() {
        return 1;
    }



    @SafeVarargs
    static <T> ArgumentParser<T> of(ArgumentParser<? extends T>... parsers) {
        return new CompositeArgumentParser<>(List.of(parsers));
    }
}