package command.argument.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StringArgParser extends AbstractSimpleArgParser<String> {

    private static final Pattern STR_PATTERN = Pattern.compile("\"(.+)\"");

    @Override
    public String parseArg(String arg) {
        Matcher matcher = STR_PATTERN.matcher(arg);
        String toReturn = null;
        if (matcher.matches()) {
            toReturn = matcher.group(1);
        }
        return toReturn;
    }
}
