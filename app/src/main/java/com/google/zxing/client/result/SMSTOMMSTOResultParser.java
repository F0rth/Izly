package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class SMSTOMMSTOResultParser extends ResultParser {
    public final SMSParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("smsto:") && !massagedText.startsWith("SMSTO:") && !massagedText.startsWith("mmsto:") && !massagedText.startsWith("MMSTO:")) {
            return null;
        }
        String substring;
        massagedText = massagedText.substring(6);
        int indexOf = massagedText.indexOf(58);
        if (indexOf >= 0) {
            substring = massagedText.substring(indexOf + 1);
            massagedText = massagedText.substring(0, indexOf);
        } else {
            substring = null;
        }
        return new SMSParsedResult(massagedText, null, null, substring);
    }
}
