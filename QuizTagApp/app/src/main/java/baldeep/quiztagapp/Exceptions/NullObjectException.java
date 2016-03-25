package baldeep.quiztagapp.Exceptions;

/**
 * This exception is to notify that a null object was found when a non-null object was expected
 */
public class NullObjectException extends Exception {
    public NullObjectException() {
        super();
    }

    public NullObjectException(String error) {
        super(error);
    }

    public NullObjectException(String error, Throwable cause) {
        super(error, cause);
    }

    public NullObjectException(Throwable cause) {
        super(cause);
    }
}
