package command.argument.parser;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class FlagArgumentParser<T> extends AbstractArgumentParser<T> {

	private final AbstractSimpleArgumentParser<T> parser;
	private final Set<Flag<T>> flags;

	public FlagArgumentParser(AbstractSimpleArgumentParser<T> parser, Set<Flag<T>> flags) {
		super(includeToCounts(flags.size()), parser.outputArgsCount());

		this.parser = parser;
		this.flags = flags;
	}

	@Override
	protected List<T> doParse(List<String> args) {
		var argument = parser.parseArg(args.get(args.size() - 1));
		if (argument == null) return null;

		for (int i = 0; i < args.size() - 1; i++) {
			final int finalI = i;
			var flag = flags.stream()
				.filter(f -> args.get(finalI).equals(f.name()))
				.findFirst();
			if (flag.isEmpty()) return null;

			argument = flag.get().action().apply(argument);
		}
		return List.of(argument);
	}


	private static  List<Integer> includeToCounts(int counts) {
		List<Integer> newList = new ArrayList<>();

		int num = 0;
		for (int i = 0; i <= counts; i++) {
			newList.add(++num);
		}
		return newList;
	}

	public static void main(String[] args) {
		Flag<String> uppercase = new Flag<>("-up", String::toUpperCase);
		Flag<String> lowercase = new Flag<>("-lw", String::toLowerCase);
		Flag<String> reverse = new Flag<>("-rev", str -> new StringBuilder(str).reverse().toString());

		FlagArgumentParser<String> stringFlagParser = new FlagArgumentParser<>(
			new StringArgumentParser(), Set.of(uppercase, lowercase, reverse)
		);
		stringFlagParser.parse(List.of("-up", "-rev", "-lw", "\"Hello wold\""))
			.ifPresent(System.out::println);
	}
}
