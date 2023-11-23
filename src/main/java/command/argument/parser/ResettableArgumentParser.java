package command.argument.parser;

public interface ResettableArgumentParser<T> extends ArgumentParser<T> {

	void reset();
}
