package rx.exceptions;

final class CompositeException$CompositeExceptionCausalChain extends RuntimeException {
    static String MESSAGE = "Chain of Causes for CompositeException In Order Received =>";
    private static final long serialVersionUID = 3875212506787802066L;

    CompositeException$CompositeExceptionCausalChain() {
    }

    public final String getMessage() {
        return MESSAGE;
    }
}
