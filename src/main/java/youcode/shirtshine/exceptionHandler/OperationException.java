package youcode.shirtshine.exceptionHandler;

public class OperationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public OperationException(String msg) {
        super(msg);
    }
}
