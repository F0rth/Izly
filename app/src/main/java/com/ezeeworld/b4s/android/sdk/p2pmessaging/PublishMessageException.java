package com.ezeeworld.b4s.android.sdk.p2pmessaging;

import com.ezeeworld.b4s.android.sdk.p2pmessaging.MessagingException.ErrorCode;

public class PublishMessageException extends MessagingException {
    private final String a;

    PublishMessageException(ErrorCode errorCode, String str, Exception exception, String str2) {
        super(errorCode, str, exception);
        this.a = str2;
    }

    public String getMessageToSend() {
        return this.a;
    }
}
