package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public final class ISBNResultParser extends ResultParser {
    public final ISBNParsedResult parse(Result result) {
        if (result.getBarcodeFormat() == BarcodeFormat.EAN_13) {
            String massagedText = ResultParser.getMassagedText(result);
            if (massagedText.length() == 13 && (massagedText.startsWith("978") || massagedText.startsWith("979"))) {
                return new ISBNParsedResult(massagedText);
            }
        }
        return null;
    }
}
