package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class SMTPResultParser extends ResultParser {
    public final EmailAddressParsedResult parse(Result result) {
        String str = null;
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("smtp:") && !massagedText.startsWith("SMTP:")) {
            return null;
        }
        String substring;
        massagedText = massagedText.substring(5);
        int indexOf = massagedText.indexOf(58);
        if (indexOf >= 0) {
            substring = massagedText.substring(indexOf + 1);
            massagedText = massagedText.substring(0, indexOf);
            indexOf = substring.indexOf(58);
            if (indexOf >= 0) {
                str = substring.substring(indexOf + 1);
                substring = substring.substring(0, indexOf);
            }
        } else {
            substring = null;
        }
        return new EmailAddressParsedResult(massagedText, substring, str, "mailto:" + massagedText);
    }
}
