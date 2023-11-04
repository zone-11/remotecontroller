package command.converter;

import command.Command;

public interface CommandConverter {

    Command parse(String inputLine);

}