package command.argument.parser;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

class FilePathArgumentParser extends AbstractArgumentParser<File> {

    private final FileType fileType;

    enum FileType {
        DIRECTORY, FILE
    }

    public FilePathArgumentParser(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    protected File doParse(String stringArg) {
        File file;
        try {
            file = Path.of(stringArg).toFile();
        } catch (InvalidPathException e) {
            return null;
        }
        return switch (fileType) {
            case FILE -> file.isFile() ? file : null;
            case DIRECTORY -> file.isDirectory() ? file : null;
            default -> file;
        };
    }
}
