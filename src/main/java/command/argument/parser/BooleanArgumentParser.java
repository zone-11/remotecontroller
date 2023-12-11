package command.argument.parser;

class BooleanArgumentParser extends AbstractArgumentParser<Boolean> {
    @Override
    protected Boolean doParse(String stringArg) {
        return stringArg.equals("true") ? Boolean.TRUE :
                (stringArg.equals("false") ? Boolean.FALSE : null);
    }
}
