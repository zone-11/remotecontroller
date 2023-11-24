package command.argument.parser;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

class FilePathArgumentParser implements ArgumentParser<File> {

    private final FileType fileType;

    public FilePathArgumentParser(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public File parse(String context) {
        File file;
        try {
            file = Path.of(context).toFile();
        } catch (InvalidPathException e) {
            e.printStackTrace();
            return null;
        }
        return switch (fileType) {
            case FILE -> file.isFile() ? file : null;
            case DIRECTORY -> file.isDirectory() ? file : null;
            default -> file;
        };
    }
}
