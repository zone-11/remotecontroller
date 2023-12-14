package command.argument.parser;

import java.util.List;
import java.util.function.Function;

public abstract class AbstractSimpleArgumentParser<T> extends AbstractArgumentParser<T> {

  AbstractSimpleArgumentParser() {
    super(List.of(1), 1);
  }

  @Override
  protected List<T> doParse(List<String> args) {
    var parsingArg = parseArg(args.get(0));
    return parsingArg != null ? List.of(parsingArg) : null;
  }

  public abstract T parseArg(String arg);



  public static <T> AbstractSimpleArgumentParser<T> from(Function<String, T> parser) {
    return new AbstractSimpleArgumentParser<T>() {
      @Override
      public T parseArg(String arg) {
        return parser.apply(arg);
      }
    };
  }
}
