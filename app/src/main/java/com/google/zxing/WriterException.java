package com.google.zxing;

public final class WriterException extends Exception {
    public WriterException(String str) {
        super(str);
    }

    public WriterException(Throwable th) {
        super(th);
    }
}
