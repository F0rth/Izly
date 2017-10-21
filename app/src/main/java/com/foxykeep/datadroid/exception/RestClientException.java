package com.foxykeep.datadroid.exception;

public class RestClientException extends Exception {
    private static final long serialVersionUID = 4658308128254827562L;
    private String mNewUrl;

    public RestClientException(String str) {
        super(str);
    }

    public RestClientException(String str, String str2) {
        super(str);
        this.mNewUrl = str2;
    }

    public RestClientException(String str, Throwable th) {
        super(str, th);
    }

    public RestClientException(Throwable th) {
        super(th);
    }

    public String getRedirectionUrl() {
        return this.mNewUrl;
    }
}
