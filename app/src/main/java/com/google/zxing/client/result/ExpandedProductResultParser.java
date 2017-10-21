package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import java.util.HashMap;
import java.util.Map;

public final class ExpandedProductResultParser extends ResultParser {
    private static String findAIvalue(int i, String str) {
        if (str.charAt(i) != '(') {
            return null;
        }
        String substring = str.substring(i + 1);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (charAt == ')') {
                return stringBuilder.toString();
            }
            if (charAt < '0' || charAt > '9') {
                return null;
            }
            stringBuilder.append(charAt);
        }
        return stringBuilder.toString();
    }

    private static String findValue(int i, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        String substring = str.substring(i);
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (charAt == '(') {
                if (findAIvalue(i2, substring) != null) {
                    break;
                }
                stringBuilder.append('(');
            } else {
                stringBuilder.append(charAt);
            }
        }
        return stringBuilder.toString();
    }

    public final ExpandedProductParsedResult parse(Result result) {
        if (result.getBarcodeFormat() != BarcodeFormat.RSS_EXPANDED) {
            return null;
        }
        String massagedText = ResultParser.getMassagedText(result);
        if (massagedText == null) {
            return null;
        }
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String str10 = null;
        String str11 = null;
        String str12 = null;
        String str13 = null;
        Map hashMap = new HashMap();
        int i = 0;
        while (i < massagedText.length()) {
            String findAIvalue = findAIvalue(i, massagedText);
            if (findAIvalue == null) {
                return null;
            }
            int length = (findAIvalue.length() + 2) + i;
            String findValue = findValue(length, massagedText);
            length += findValue.length();
            if ("00".equals(findAIvalue)) {
                str2 = findValue;
                i = length;
            } else if ("01".equals(findAIvalue)) {
                str = findValue;
                i = length;
            } else if ("10".equals(findAIvalue)) {
                str3 = findValue;
                i = length;
            } else if ("11".equals(findAIvalue)) {
                str4 = findValue;
                i = length;
            } else if ("13".equals(findAIvalue)) {
                str5 = findValue;
                i = length;
            } else if ("15".equals(findAIvalue)) {
                str6 = findValue;
                i = length;
            } else if ("17".equals(findAIvalue)) {
                str7 = findValue;
                i = length;
            } else if ("3100".equals(findAIvalue) || "3101".equals(findAIvalue) || "3102".equals(findAIvalue) || "3103".equals(findAIvalue) || "3104".equals(findAIvalue) || "3105".equals(findAIvalue) || "3106".equals(findAIvalue) || "3107".equals(findAIvalue) || "3108".equals(findAIvalue) || "3109".equals(findAIvalue)) {
                str9 = ExpandedProductParsedResult.KILOGRAM;
                str10 = findAIvalue.substring(3);
                str8 = findValue;
                i = length;
            } else if ("3200".equals(findAIvalue) || "3201".equals(findAIvalue) || "3202".equals(findAIvalue) || "3203".equals(findAIvalue) || "3204".equals(findAIvalue) || "3205".equals(findAIvalue) || "3206".equals(findAIvalue) || "3207".equals(findAIvalue) || "3208".equals(findAIvalue) || "3209".equals(findAIvalue)) {
                str9 = ExpandedProductParsedResult.POUND;
                str10 = findAIvalue.substring(3);
                str8 = findValue;
                i = length;
            } else if ("3920".equals(findAIvalue) || "3921".equals(findAIvalue) || "3922".equals(findAIvalue) || "3923".equals(findAIvalue)) {
                str12 = findAIvalue.substring(3);
                str11 = findValue;
                i = length;
            } else if (!"3930".equals(findAIvalue) && !"3931".equals(findAIvalue) && !"3932".equals(findAIvalue) && !"3933".equals(findAIvalue)) {
                hashMap.put(findAIvalue, findValue);
                i = length;
            } else if (findValue.length() < 4) {
                return null;
            } else {
                str11 = findValue.substring(3);
                str13 = findValue.substring(0, 3);
                str12 = findAIvalue.substring(3);
                i = length;
            }
        }
        return new ExpandedProductParsedResult(massagedText, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, hashMap);
    }
}
