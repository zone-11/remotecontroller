package command.argument.parser;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractArgumentParser<T> implements ArgumentParser<T> {

  private static final List<Integer> DEFAULT_INPUT_ARGS_COUNTS = List.of(1);
  private static final int DEFAULT_OUTPUT_ARGS_COUNT = 1;

  private final List<Integer> possibleInputArgsCounts;
  private final int outputArgsCount;

  AbstractArgumentParser(List<Integer> possibleInputArgsCounts, int outputArgsCount) {
    this.possibleInputArgsCounts = possibleInputArgsCounts;
    this.outputArgsCount = outputArgsCount;
  }

  AbstractArgumentParser() {
    this.possibleInputArgsCounts = DEFAULT_INPUT_ARGS_COUNTS;
    this.outputArgsCount = DEFAULT_OUTPUT_ARGS_COUNT;
  }

  @Override
  public Optional<List<T>> parse(List<String> args) {
    if (args.isEmpty() || !canParse(args.size())) {
      return Optional.empty();
    }

    List<T> list = args.stream()
      .map(this::doParse)
      .filter(Objects::nonNull)
      .toList();
    return list.size() == outputArgsCount ? Optional.of(list) : Optional.empty();
  }

  public boolean canParse(int count) {
    return possibleInputArgsCounts.contains(count);
  }

  public int outputArgsCount() {
    return outputArgsCount;
  }

  public List<Integer> inputArgsCounts() {
    return possibleInputArgsCounts;
  }

  protected abstract T doParse(String stringArg);

  // can override this to reset your parser
  protected void reset() {}
}
