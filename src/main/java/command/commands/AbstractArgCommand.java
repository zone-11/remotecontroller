package command.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

abstract class AbstractArgCommand extends AbstractSimpleCommand {

  private final List<String> strArgs;

  AbstractArgCommand(String name,
                     Runnable defaultAction) {
    super(name, defaultAction);
    this.strArgs = new ArrayList<>();
  }

  @Override
  public void execute() {
    if (strArgs.isEmpty()) {
      super.execute();
      return;
    }
    doExecute(strArgs);
  }

  @Override
  public final Function<String, AbstractArgCommand> parser() {
    return context -> {
      strArgs.add(context);
      return this;
    };
  }

  protected abstract void doExecute(List<String> strArgs);
}
