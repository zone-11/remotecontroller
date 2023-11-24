package command.argument.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StringArgumentParser implements ArgumentParser<String> {

    private static final Pattern STR_PATTERN = Pattern.compile("\"(.+)\"");

    public String parse(String context) {
        Matcher matcher = STR_PATTERN.matcher(context);
        String toReturn = null;
        if (matcher.matches()) {
            toReturn = matcher.group(1);
        }
        return toReturn;
    }
}
