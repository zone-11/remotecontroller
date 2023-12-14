package command.argument.parser;

class IntegerArgumentParser extends AbstractSimpleArgumentParser<Integer> {

    @Override
    public Integer parseArg(String stringArg) {
        Integer toReturn = null;
        try {
            toReturn = Integer.parseInt(stringArg);
        } finally {
            return toReturn;
        }
    }
}