package command.argument.parser;


class FlagArgumentParser<T> extends AbstractArgumentParser<T> {

	private final AbstractArgumentParser<T> parser;
	private final FlagArgumentParser<T> childFlagParser;
	private final Flag<T> flag;

	public FlagArgumentParser(AbstractArgumentParser<T> parser, Flag<T> flag) {
		super(includeToCounts(parser.inputArgsCounts()), parser.outputArgsCount());

		this.flag = flag;
		if (parser instanceof FlagArgumentParser<T> flagParser) {
			this.parser = flagParser.parser;
			this.childFlagParser = flagParser;
		} else {
			this.parser = parser;
			this.childFlagParser = null;
		}
	}

	@Override
	protected List<T> doParse(List<String> args) {
		var stringFlags = args.subList(0, args.size() - 1);
		var parsingArgs = parser.parse(List.of(args.get(args.size() - 1)));

		if (parsingArgs.isEmpty()) return null;

		var parsingArg = parsingArgs.get().get(0);
		for (String arg : stringFlags) {
			var flag = parseFlag(arg);
			if (flag != null) {
				parsingArg = flag.action().apply(parsingArg);
			} else {
				return null;
			}
		}
		return List.of(parsingArg);
	}

	private Flag<T> parseFlag(String arg) {
		return arg.equals(flag.name()) ? flag :
			(childFlagParser != null ? childFlagParser.parseFlag(arg) : null);
	}

	private static  List<Integer> includeToCounts(List<Integer> counts) {
		var newList = new ArrayList<>(counts);
		newList.add(counts.get(counts.size() - 1) + 1);
		return newList;
	}

	public static void main(String[] args) {
		Flag<String> uppercase = new Flag<>("-up", String::toUpperCase);
		Flag<String> lowercase = new Flag<>("-lw", String::toLowerCase);
		Flag<String> reverse = new Flag<>("-rev", str -> new StringBuilder(str).reverse().toString());

		FlagArgumentParser<String> stringFlagParser = new FlagArgumentParser<>(
			new FlagArgumentParser<>(new FlagArgumentParser<>(new StringArgumentParser(),
				reverse), uppercase), lowercase
		);
		stringFlagParser.parse(List.of("-up"))
			.ifPresent(System.out::println);
	}
}
