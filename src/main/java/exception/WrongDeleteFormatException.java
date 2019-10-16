package exception;

public class WrongDeleteFormatException extends WrongFormatException {
    public WrongDeleteFormatException() {
        super("     OOPS: Expected format \"delete w/WORD_TO_BE_DELETED\"");
    }
}
