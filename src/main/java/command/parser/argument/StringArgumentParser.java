package command.parser.argument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import command.parser.ArgumentParser;

public class StringArgumentParser implements ArgumentParser<String> {

    Pattern pattern = Pattern.compile("\"(.+)\"");

    public String parse(String context) {
        Matcher matcher = pattern.matcher(context);

        if (matcher.matches()) {
            return matcher.group(1);
        }
        return context;
    }

    public boolean canParse(String context) {
        return pattern.matcher(context).matches();
    }

}
