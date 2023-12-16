package command.commands;

import command.Command;
import command.argument.parser.AbstractSimpleArgumentParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ParameterCommand<T> extends AbstractArgCommand {

  private final List<Parameter<? extends T>> parameters;
  private final Consumer<Map<String, T>> action;

  public ParameterCommand(String name,
                          Consumer<Map<String, T>> action,
                          Runnable defaultAction,
                          List<Parameter<? extends T>> parameters) {
    super(name, defaultAction);
    this.parameters = parameters;
    this.action = action;
  }

  @Override
  protected void doExecute(List<String> strArgs) {
    var iterator = strArgs.listIterator();
    while (iterator.hasNext()) {
      var next = iterator.next();
      var param = parameters.stream()
        .filter(p -> next.equals(p.name))
        .findAny();

      if (param.isEmpty() || !iterator.hasNext()) throw new IllegalArgumentException();

      param.get().setValue(iterator.next());
    }

    action.accept(parameters.stream()
      .collect(Collectors.toMap(Parameter::name, Parameter::value)));
  }


  public static class Parameter<T> {

    private final String name;
    private final AbstractSimpleArgumentParser<T> paramValueParser;
    private final Supplier<T> defaultParamValue;

    private T value = null;

    public Parameter(String name,
                     AbstractSimpleArgumentParser<T> paramValueParser,
                     Supplier<T> defaultParamValue) {
      this.name = name;
      this.paramValueParser = paramValueParser;
      this.defaultParamValue = defaultParamValue;
    }

    private String name() {
      return name;
    }

    private T value() {
      return value != null ? value : defaultParamValue.get();
    }

    private void setValue(String str) {
      var parsingValue = paramValueParser.parseArg(str);
      if (parsingValue == null) return;
      value = parsingValue;
    }
  }

  public static class Builder extends Command.Builder<Builder> {

    private final List<Parameter<?>> parameters = new ArrayList<>();
    private Consumer<Map<String, Object>> paramAction;

    public <T> Builder parameter(String name,
                                AbstractSimpleArgumentParser<T> valueParser,
                                Supplier<T> defaultValue) {
      parameters.add(new Parameter<>(name, valueParser, defaultValue));
      return this;
    }

    public Builder paramAction(Consumer<Map<String, Object>> paramAction) {
      this.paramAction = paramAction;
      return this;
    }

    @Override
    protected Command hookBuild(String name, Runnable action) {
      if (action == null) throw new IllegalArgumentException("action");
      return new ParameterCommand<>(name, paramAction, action, parameters);
    }

    @Override
    protected ParameterCommand.Builder self() {
      return this;
    }
  }
}