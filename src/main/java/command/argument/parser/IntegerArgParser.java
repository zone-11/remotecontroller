package command.argument.parser;

class IntegerArgParser extends AbstractSimpleArgParser<Integer> {

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