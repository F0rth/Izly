package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class WifiResultParser extends ResultParser {
    public final WifiParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        if (massagedText.startsWith("WIFI:")) {
            String matchSinglePrefixedField = ResultParser.matchSinglePrefixedField("S:", massagedText, ';', false);
            if (!(matchSinglePrefixedField == null || matchSinglePrefixedField.length() == 0)) {
                String matchSinglePrefixedField2 = ResultParser.matchSinglePrefixedField("P:", massagedText, ';', false);
                String matchSinglePrefixedField3 = ResultParser.matchSinglePrefixedField("T:", massagedText, ';', false);
                if (matchSinglePrefixedField3 == null) {
                    matchSinglePrefixedField3 = "nopass";
                }
                return new WifiParsedResult(matchSinglePrefixedField3, matchSinglePrefixedField, matchSinglePrefixedField2, Boolean.parseBoolean(ResultParser.matchSinglePrefixedField("H:", massagedText, ';', false)));
            }
        }
        return null;
    }
}
