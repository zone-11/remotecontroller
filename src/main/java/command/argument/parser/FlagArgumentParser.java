package command.argument.parser;


import java.util.regex.Pattern;

class FlagArgumentParser implements ArgumentParser<String> {

	private static final Pattern SHORT_FLAG = Pattern.compile("-[a-z]{1,3}");
	private static final Pattern FULL_FLAG = Pattern.compile("--\\w+");

	private String flag;

	public FlagArgumentParser(String flag) {
		if (SHORT_FLAG.matcher(flag).matches() ||
			FULL_FLAG.matcher(flag).matches()) {
			this.flag = flag;
		} else {
			throw new IllegalArgumentException("the flag name is illegal");
		}
	}

	@Override
	public String parse(String context) {
		return flag.equals(context) ? flag : null;
	}
}
