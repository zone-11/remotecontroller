package command.argument.parser;

import java.util.*;

public abstract class AbstractArgParser<T> implements ArgParser<T> {

  private final List<Integer> possibleInputArgsCounts;
  private final int outputArgsCount;

  AbstractArgParser(List<Integer> possibleInputArgsCounts, int outputArgsCount) {
    this.possibleInputArgsCounts = possibleInputArgsCounts;
    this.outputArgsCount = outputArgsCount;
  }

  @Override
  public Optional<List<T>> parse(List<String> args) {
    if (args.isEmpty() || !canParse(args.size())) {
      return Optional.empty();
    }

    var parsingArgs = doParse(args);
    return parsingArgs != null ? Optional.of(parsingArgs) : Optional.empty();
  }

  public boolean canParse(int count) {
    return possibleInputArgsCounts.contains(count);
  }

  public int outputArgsCount() {
    return outputArgsCount;
  }

  public List<Integer> inputArgsCounts() {
    return List.copyOf(possibleInputArgsCounts);
  }

  protected abstract List<T> doParse(List<String> args);

  // can override this to reset your parser
  protected void reset() {}
}
