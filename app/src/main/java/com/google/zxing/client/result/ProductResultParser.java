package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.oned.UPCEReader;

public final class ProductResultParser extends ResultParser {
    public final ProductParsedResult parse(Result result) {
        BarcodeFormat barcodeFormat = result.getBarcodeFormat();
        if (barcodeFormat == BarcodeFormat.UPC_A || barcodeFormat == BarcodeFormat.UPC_E || barcodeFormat == BarcodeFormat.EAN_8 || barcodeFormat == BarcodeFormat.EAN_13) {
            String massagedText = ResultParser.getMassagedText(result);
            int length = massagedText.length();
            int i = 0;
            while (i < length) {
                char charAt = massagedText.charAt(i);
                if (charAt >= '0' && charAt <= '9') {
                    i++;
                }
            }
            return new ProductParsedResult(massagedText, barcodeFormat == BarcodeFormat.UPC_E ? UPCEReader.convertUPCEtoUPCA(massagedText) : massagedText);
        }
        return null;
    }
}
