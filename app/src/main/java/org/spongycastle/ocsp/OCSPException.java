package org.spongycastle.ocsp;

public class OCSPException extends Exception {
    Exception e;

    public OCSPException(String str) {
        super(str);
    }

    public OCSPException(String str, Exception exception) {
        super(str);
        this.e = exception;
    }

    public Throwable getCause() {
        return this.e;
    }

    public Exception getUnderlyingException() {
        return this.e;
    }
}
