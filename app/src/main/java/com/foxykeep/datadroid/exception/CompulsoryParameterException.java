package com.foxykeep.datadroid.exception;

public class CompulsoryParameterException extends RuntimeException {
    private static final long serialVersionUID = -6031863210486494461L;

    public CompulsoryParameterException(String str) {
        super(str);
    }

    public CompulsoryParameterException(String str, Throwable th) {
        super(str, th);
    }

    public CompulsoryParameterException(Throwable th) {
        super(th);
    }
}
