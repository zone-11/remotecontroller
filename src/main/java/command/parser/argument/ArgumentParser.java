package command.argumentparser;

public interface ArgumentParser<T> {

    public T parse(String arg);

    public boolean canParse(String context);

}