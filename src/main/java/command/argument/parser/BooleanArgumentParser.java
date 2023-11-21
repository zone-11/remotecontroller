package command.argument.parser;

class BooleanArgumentParser implements ArgumentParser<Boolean> {
    @Override
    public Boolean parse(String context) {
        return context.equals("true") ? Boolean.TRUE :
                (context.equals("false") ? Boolean.FALSE : null);
    }
}
