package command.argument.parser;

class IntegerArgumentParser extends AbstractArgumentParser<Integer> {

    @Override
    protected Integer doParse(String stringArg) {
        Integer toReturn = null;
        try {
            toReturn = Integer.parseInt(stringArg);
        } finally {
            return toReturn;
        }
    }
}