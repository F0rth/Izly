package com.google.zxing.client.result;

import java.util.regex.Pattern;

public final class URIParsedResult extends ParsedResult {
    private static final Pattern USER_IN_HOST = Pattern.compile(":/*([^/@]+)@[^/]+");
    private final String title;
    private final String uri;

    public URIParsedResult(String str, String str2) {
        super(ParsedResultType.URI);
        this.uri = massageURI(str);
        this.title = str2;
    }

    private static boolean isColonFollowedByPortNumber(String str, int i) {
        int indexOf = str.indexOf(47, i + 1);
        int length = indexOf < 0 ? str.length() : indexOf;
        if (length > i + 1) {
            indexOf = i + 1;
            while (indexOf < length) {
                if (str.charAt(indexOf) >= '0' && str.charAt(indexOf) <= '9') {
                    indexOf++;
                }
            }
            return true;
        }
        return false;
    }

    private static String massageURI(String str) {
        String trim = str.trim();
        int indexOf = trim.indexOf(58);
        return indexOf < 0 ? "http://" + trim : isColonFollowedByPortNumber(trim, indexOf) ? "http://" + trim : trim;
    }

    public final String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(30);
        ParsedResult.maybeAppend(this.title, stringBuilder);
        ParsedResult.maybeAppend(this.uri, stringBuilder);
        return stringBuilder.toString();
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getURI() {
        return this.uri;
    }

    public final boolean isPossiblyMaliciousURI() {
        return USER_IN_HOST.matcher(this.uri).find();
    }
}
