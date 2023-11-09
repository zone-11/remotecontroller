package command.argument.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringArgumentParser implements ArgumentParser<String> {

    Pattern pattern = Pattern.compile("\"(.+)\"");

    public String parse(String context) {
        Matcher matcher = pattern.matcher(context);

        if (matcher.matches()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Expects string argument");
    }

    public boolean canParse(String context) {
        return pattern.matcher(context).matches();
    }

}
