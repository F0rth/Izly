package com.google.zxing.client.result;

public final class EmailAddressParsedResult extends ParsedResult {
    private final String body;
    private final String emailAddress;
    private final String mailtoURI;
    private final String subject;

    EmailAddressParsedResult(String str, String str2, String str3, String str4) {
        super(ParsedResultType.EMAIL_ADDRESS);
        this.emailAddress = str;
        this.subject = str2;
        this.body = str3;
        this.mailtoURI = str4;
    }

    public final String getBody() {
        return this.body;
    }

    public final String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(30);
        ParsedResult.maybeAppend(this.emailAddress, stringBuilder);
        ParsedResult.maybeAppend(this.subject, stringBuilder);
        ParsedResult.maybeAppend(this.body, stringBuilder);
        return stringBuilder.toString();
    }

    public final String getEmailAddress() {
        return this.emailAddress;
    }

    public final String getMailtoURI() {
        return this.mailtoURI;
    }

    public final String getSubject() {
        return this.subject;
    }
}
