package command.argument.parser.impl;

import command.argument.parser.ArgumentParser;

public class IntegerArgumentParser implements ArgumentParser<Integer> {

    @Override
    public Integer parse(String context) {
        Integer toReturn = null;
        try {
           toReturn = Integer.parseInt(context);
        } finally {
            return toReturn;
        }

    }

}
