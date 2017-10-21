package com.google.zxing.client.result;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URIResultParser extends ResultParser {
    private static final String ALPHANUM_PART = "[a-zA-Z0-9\\-]";
    private static final Pattern URL_WITHOUT_PROTOCOL_PATTERN = Pattern.compile("([a-zA-Z0-9\\-]+\\.)+[a-zA-Z0-9\\-]{2,}(:\\d{1,5})?(/|\\?|$)");
    private static final Pattern URL_WITH_PROTOCOL_PATTERN = Pattern.compile("[a-zA-Z0-9]{2,}:");

    static boolean isBasicallyValidURI(String str) {
        if (!str.contains(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
            Matcher matcher = URL_WITH_PROTOCOL_PATTERN.matcher(str);
            if (matcher.find() && matcher.start() == 0) {
                return true;
            }
            matcher = URL_WITHOUT_PROTOCOL_PATTERN.matcher(str);
            if (matcher.find() && matcher.start() == 0) {
                return true;
            }
        }
        return false;
    }

    public final URIParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        if (massagedText.startsWith("URL:") || massagedText.startsWith("URI:")) {
            return new URIParsedResult(massagedText.substring(4).trim(), null);
        }
        massagedText = massagedText.trim();
        return isBasicallyValidURI(massagedText) ? new URIParsedResult(massagedText, null) : null;
    }
}
