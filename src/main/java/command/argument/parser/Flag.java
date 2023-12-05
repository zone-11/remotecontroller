package command.argument.parser;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public record Flag<T>(String name, UnaryOperator<T> action) {

  private static final Pattern SHORT_FLAG = Pattern.compile("-[a-z]{1,3}");
  private static final Pattern FULL_FLAG = Pattern.compile("--\\w+");

  public Flag {
    if (!(SHORT_FLAG.matcher(name).matches() || FULL_FLAG.matcher(name).matches())) {
      throw new IllegalArgumentException("flag name is incorrect");
    }
  }
}