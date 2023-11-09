package command.argument.parser;

import java.util.Iterator;
import java.util.List;

public interface ArgumentParser<T> {

    T parse(String context);

}



