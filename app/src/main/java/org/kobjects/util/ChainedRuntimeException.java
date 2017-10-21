package org.kobjects.util;

public class ChainedRuntimeException extends RuntimeException {
    Exception chain;

    ChainedRuntimeException() {
    }

    ChainedRuntimeException(Exception exception, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        if (str == null) {
            str = "rethrown";
        }
        super(stringBuilder.append(str).append(": ").append(exception.toString()).toString());
        this.chain = exception;
    }

    public static ChainedRuntimeException create(Exception exception, String str) {
        try {
            return ((ChainedRuntimeException) Class.forName("org.kobjects.util.ChainedRuntimeExceptionSE").newInstance())._create(exception, str);
        } catch (Exception e) {
            return new ChainedRuntimeException(exception, str);
        }
    }

    ChainedRuntimeException _create(Exception exception, String str) {
        throw new RuntimeException("ERR!");
    }

    public Exception getChained() {
        return this.chain;
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (this.chain != null) {
            this.chain.printStackTrace();
        }
    }
}
