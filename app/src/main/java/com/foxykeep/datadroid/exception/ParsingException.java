package com.foxykeep.datadroid.exception;

public class ParsingException extends Exception {
    private static final long serialVersionUID = -6031863210486494461L;

    public ParsingException(String str) {
        super(str);
    }

    public ParsingException(String str, Throwable th) {
        super(str, th);
    }

    public ParsingException(Throwable th) {
        super(th);
    }
}
