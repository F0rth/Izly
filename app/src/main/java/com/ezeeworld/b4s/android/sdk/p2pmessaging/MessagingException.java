package com.ezeeworld.b4s.android.sdk.p2pmessaging;

public class MessagingException extends Exception {
    private final ErrorCode a;

    public enum ErrorCode {
        BluetoothUnavailable,
        DeviceNotNearby,
        CommunicationFailed,
        UnsupportedObject,
        Busy
    }

    MessagingException(ErrorCode errorCode, String str, Throwable th) {
        super(str, th);
        this.a = errorCode;
    }

    public ErrorCode getCode() {
        return this.a;
    }

    public Throwable getInternalException() {
        return super.getCause();
    }

    public String getInternalMessage() {
        return super.getMessage();
    }
}
