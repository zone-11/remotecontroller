package command.commands;

import command.Command;
import command.argument.parser.ArgumentParser;
import command.argument.parser.CompositeArgumentParser;
import command.argument.parser.IntegerArgumentParser;
import command.argument.parser.StringArgumentParser;

import java.util.Arrays;

public class Print2Command extends ArgumentCommand<Object> {

    public Print2Command(Command command) {
        super(2, command, new CompositeArgumentParser(
                Arrays.asList(new StringArgumentParser(), new IntegerArgumentParser())
        ));
    }

    @Override
    public void execute() {
        String text = (String) arguments.get(0);
        int repeatQuantity = 1;

        if (arguments.size() > 1) {
            repeatQuantity = (int)arguments.get(1);
        }

        for (int i = 0; i < repeatQuantity; i++) {
            System.out.println(i + " Message: " + text);
        }
    }

    @Override
    public String getName() {
        return "print2";
    }

}
