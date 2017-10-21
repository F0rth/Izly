package com.google.zxing.client.result;

public final class TelParsedResult extends ParsedResult {
    private final String number;
    private final String telURI;
    private final String title;

    public TelParsedResult(String str, String str2, String str3) {
        super(ParsedResultType.TEL);
        this.number = str;
        this.telURI = str2;
        this.title = str3;
    }

    public final String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(20);
        ParsedResult.maybeAppend(this.number, stringBuilder);
        ParsedResult.maybeAppend(this.title, stringBuilder);
        return stringBuilder.toString();
    }

    public final String getNumber() {
        return this.number;
    }

    public final String getTelURI() {
        return this.telURI;
    }

    public final String getTitle() {
        return this.title;
    }
}
