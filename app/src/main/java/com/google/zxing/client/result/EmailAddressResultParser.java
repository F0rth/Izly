package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Map;

public final class EmailAddressResultParser extends ResultParser {
    public final EmailAddressParsedResult parse(Result result) {
        String str = null;
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("mailto:") && !massagedText.startsWith("MAILTO:")) {
            return EmailDoCoMoResultParser.isBasicallyValidEmailAddress(massagedText) ? new EmailAddressParsedResult(massagedText, null, null, "mailto:" + massagedText) : null;
        } else {
            String str2;
            String str3;
            String substring = massagedText.substring(7);
            int indexOf = substring.indexOf(63);
            if (indexOf >= 0) {
                substring = substring.substring(0, indexOf);
            }
            substring = ResultParser.urlDecode(substring);
            Map parseNameValuePairs = ResultParser.parseNameValuePairs(massagedText);
            if (parseNameValuePairs != null) {
                str2 = substring.length() == 0 ? (String) parseNameValuePairs.get("to") : substring;
                str = (String) parseNameValuePairs.get("body");
                str3 = (String) parseNameValuePairs.get("subject");
            } else {
                str2 = substring;
                str3 = null;
            }
            return new EmailAddressParsedResult(str2, str3, str, massagedText);
        }
    }
}
