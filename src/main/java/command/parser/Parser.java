package command.parser;

public interface Parser<T> {

    public T parse(String context);

}
