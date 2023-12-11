package command.argument.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StringArgumentParser extends AbstractArgumentParser<String>  {

    private static final Pattern STR_PATTERN = Pattern.compile("\"(.+)\"");

    @Override
    protected String doParse(String stringArg) {
        Matcher matcher = STR_PATTERN.matcher(stringArg);
        String toReturn = null;
        if (matcher.matches()) {
            toReturn = matcher.group(1);
        }
        return toReturn;
    }
}
