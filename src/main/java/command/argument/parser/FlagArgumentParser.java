package command.argument.parser;


import java.util.function.Function;
import java.util.regex.Pattern;

class FlagArgumentParser<T> implements ResettableArgumentParser<T> {

	private static final Pattern SHORT_FLAG = Pattern.compile("-[a-z]{1,3}");
	private static final Pattern FULL_FLAG = Pattern.compile("--\\w+");

	private final String flag;
	private final ArgumentParser<T> parser;
	private final Function<T, T> flagAction;

	private boolean isFlagDetected;

	public FlagArgumentParser(ArgumentParser<T> parser,
														String flag,
														Function<T, T> flagAction) {
		if (SHORT_FLAG.matcher(flag).matches() ||
				FULL_FLAG.matcher(flag).matches()) {
			this.flag = flag;
			this.parser = parser;
			this.flagAction = flagAction;
		} else {
			throw new IllegalArgumentException("the flag name is illegal");
		}
	}

	@Override
	public T parse(String context) {
		if (isFlagDetected) {
			var parsingValue = parser.parse(context);
			return parsingValue != null ? flagAction.apply(parsingValue) : null;
		} else if (context.equals(flag)) {
			isFlagDetected = true;
		}
		return null;
	}

	@Override
	public int inputArgsCount() {
		return parser.inputArgsCount() + 1;
	}

	@Override
	public int outputArgsCount() {
		return parser.outputArgsCount();
	}

	@Override
	public void reset() {
		isFlagDetected = false;
	}
}
