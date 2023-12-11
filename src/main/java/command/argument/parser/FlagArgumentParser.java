package command.argument.parser;


class FlagArgumentParser<T> extends AbstractArgumentParser<T> {

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
	protected T doParse(String stringArg) {
		switch (detection) {
			case WAITING -> {
				if (stringArg.equals(flag.name())) {
					detection = Detection.SUCCESS;
				} else if (childFlagParser != null) {
					detection = Detection.SUCCESS_CHILD;
					return childFlagParser.doParse(stringArg);
				} else {
					detection = Detection.FAILED;
				}
				return null;
			}
			case SUCCESS -> {
			}
			case SUCCESS_CHILD -> {
				return childFlagParser.doParse(stringArg);
			}
			default -> {
				return null;
			}
		}
		return null;
	}
}
