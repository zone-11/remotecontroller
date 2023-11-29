package command.converter;

import java.util.regex.Pattern;

public enum CommandSeparator {

	SPACE("\"[^\"]+\"|\\S+");

	private final Pattern pattern;

	CommandSeparator(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}

	public Pattern pattern() {
		return pattern;
	}
}
