package command.converter;

import command.commands.ArgumentCommand;
import command.Command;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DefaultCommandConverter implements CommandConverter {

    Pattern commandPartPattern = Pattern.compile("(\".*\"|\\S+)\\s+");

    @Override
    public Command parse(String inputLine) {
        Matcher matcher = commandPartPattern.matcher(inputLine + " ");
        List<String> commands = matcher.results()
                .map(result -> result.group(1))
                .toList();
        String commandPart = commands.get(commands.size() - 1);
        Command parent = null;

        if (commands.size() > 1) {
            parent = parse(inputLine.substring(0, inputLine.length() - commandPart.length()));
        } else {
            return Command.findByName(commandPart).orElseThrow();
        }

        if (parent instanceof ArgumentCommand<?> argCommand) {
            argCommand.addArgument(commandPart);
            return argCommand;
        }
        return parent.getChildCommand(commandPart).orElseThrow();
    }
    public static void main(String[] args) {}

}

