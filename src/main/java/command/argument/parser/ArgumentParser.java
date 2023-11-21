package command.argument.parser;

import java.util.List;

public interface ArgumentParser<T> {

    T parse(String context);

    default int getArgumentsNumber() {
        return 1;
    }

    interface Resettable {

        void reset();
    }

    static ArgumentParser<?> of(ArgumentParser<?>... parsers) {
        return new CompositeArgumentParser(List.of(parsers));
    }

}



