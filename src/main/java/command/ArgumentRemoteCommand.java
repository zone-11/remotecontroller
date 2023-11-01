package command;

import command.parser.argument.ArgumentParser;

public abstract class ArgumentRemoteCommand<T> implements RemoteCommand {

    protected T argument;

    public boolean setArgument(String argumentText) {
        if (getArgumentParser().canParse(argumentText)) {
            argument = getArgumentParser().parse(argumentText);
            return true;
        }
        return false;
    }

    protected abstract ArgumentParser<T> getArgumentParser();

}
