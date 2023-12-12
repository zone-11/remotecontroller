package command.argument.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractArgumentParser<T> implements ArgumentParser<T> {

  private static final int[] DEFAULT_INPUT_ARGS_COUNTS = {1};
  private static final int DEFAULT_OUTPUT_ARGS_COUNT = 1;

  private final int[] possibleInputArgsCounts;
  private final int outputArgsCount;

  AbstractArgumentParser(int[] possibleInputArgsCounts, int outputArgsCount) {
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
    return Arrays.binarySearch(possibleInputArgsCounts, count) != -1;
  }

  public int outputArgsCount() {
    return outputArgsCount;
  }

  public int[] inputArgsCounts() {
    return possibleInputArgsCounts;
  }

  protected abstract T doParse(String stringArg);

  // can override this to reset your parser
  protected void reset() {}
}
