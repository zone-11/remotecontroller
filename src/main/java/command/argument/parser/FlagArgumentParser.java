package command.argument.parser;


class FlagArgumentParser<T> implements ResettableArgumentParser<T> {

	private final ArgumentParser<T> parser;
	private final FlagArgumentParser<T> childFlagParser;
	private final Flag<T> flag;

	private Detection detection = Detection.WAITING;

	public FlagArgumentParser(ArgumentParser<T> parser, Flag<T> flag) {
		this.flag = flag;
		if (parser instanceof FlagArgumentParser<T> flagParser) {
			this.parser = flagParser.parser;
			this.childFlagParser = flagParser;
		} else {
			this.parser = parser;
			this.childFlagParser = null;
		}
	}

	private enum Detection {
		SUCCESS,
		SUCCESS_CHILD,
		WAITING,
		FAILED
	}

	@Override
	public T parse(String context) {
		switch (detection) {
			case WAITING -> {
				if (context.equals(flag.name())) {
					detection = Detection.SUCCESS;
				} else if (childFlagParser != null) {
					detection = Detection.SUCCESS_CHILD;
					return childFlagParser.parse(context);
				} else {
					detection = Detection.FAILED;
				}
				return null;
			}
			case SUCCESS -> {
				var parsingValue = parser.parse(context);
				return parsingValue != null ? flag.action().apply(parsingValue) : null;
			}
			case SUCCESS_CHILD -> {
				return childFlagParser.parse(context);
			}
			default -> {
				return null;
			}
		}
	}

	@Override
	public int inputArgsCount() {
		return parser instanceof FlagArgumentParser<?>
			? parser.inputArgsCount()
			: parser.inputArgsCount() + 1;
	}

	@Override
	public int outputArgsCount() {
		return parser.outputArgsCount();
	}

	@Override
	public void reset() {
		detection = Detection.WAITING;
		if (childFlagParser != null) {
			childFlagParser.reset();
		} else if (parser instanceof ResettableArgumentParser<T> resetParser){
			resetParser.reset();
		}
	}
}
