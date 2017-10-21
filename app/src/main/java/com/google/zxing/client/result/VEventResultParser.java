package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.List;

public final class VEventResultParser extends ResultParser {
    private static String matchSingleVCardPrefixedField(CharSequence charSequence, String str, boolean z) {
        List matchSingleVCardPrefixedField = VCardResultParser.matchSingleVCardPrefixedField(charSequence, str, z, false);
        return (matchSingleVCardPrefixedField == null || matchSingleVCardPrefixedField.isEmpty()) ? null : (String) matchSingleVCardPrefixedField.get(0);
    }

    private static String[] matchVCardPrefixedField(CharSequence charSequence, String str, boolean z) {
        List matchVCardPrefixedField = VCardResultParser.matchVCardPrefixedField(charSequence, str, z, false);
        if (matchVCardPrefixedField == null || matchVCardPrefixedField.isEmpty()) {
            return null;
        }
        int size = matchVCardPrefixedField.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = (String) ((List) matchVCardPrefixedField.get(i)).get(0);
        }
        return strArr;
    }

    private static String stripMailto(String str) {
        return str != null ? (str.startsWith("mailto:") || str.startsWith("MAILTO:")) ? str.substring(7) : str : str;
    }

    public final CalendarParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        if (massagedText.indexOf("BEGIN:VEVENT") < 0) {
            return null;
        }
        String matchSingleVCardPrefixedField = matchSingleVCardPrefixedField("SUMMARY", massagedText, true);
        String matchSingleVCardPrefixedField2 = matchSingleVCardPrefixedField("DTSTART", massagedText, true);
        if (matchSingleVCardPrefixedField2 == null) {
            return null;
        }
        double d;
        double d2;
        String matchSingleVCardPrefixedField3 = matchSingleVCardPrefixedField("DTEND", massagedText, true);
        String matchSingleVCardPrefixedField4 = matchSingleVCardPrefixedField("DURATION", massagedText, true);
        String matchSingleVCardPrefixedField5 = matchSingleVCardPrefixedField("LOCATION", massagedText, true);
        String stripMailto = stripMailto(matchSingleVCardPrefixedField("ORGANIZER", massagedText, true));
        String[] matchVCardPrefixedField = matchVCardPrefixedField("ATTENDEE", massagedText, true);
        if (matchVCardPrefixedField != null) {
            for (int i = 0; i < matchVCardPrefixedField.length; i++) {
                matchVCardPrefixedField[i] = stripMailto(matchVCardPrefixedField[i]);
            }
        }
        String matchSingleVCardPrefixedField6 = matchSingleVCardPrefixedField("DESCRIPTION", massagedText, true);
        String matchSingleVCardPrefixedField7 = matchSingleVCardPrefixedField("GEO", massagedText, true);
        if (matchSingleVCardPrefixedField7 == null) {
            d = Double.NaN;
            d2 = Double.NaN;
        } else {
            int indexOf = matchSingleVCardPrefixedField7.indexOf(59);
            try {
                d = Double.parseDouble(matchSingleVCardPrefixedField7.substring(0, indexOf));
                d2 = Double.parseDouble(matchSingleVCardPrefixedField7.substring(indexOf + 1));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        try {
            return new CalendarParsedResult(matchSingleVCardPrefixedField, matchSingleVCardPrefixedField2, matchSingleVCardPrefixedField3, matchSingleVCardPrefixedField4, matchSingleVCardPrefixedField5, stripMailto, matchVCardPrefixedField, matchSingleVCardPrefixedField6, d, d2);
        } catch (IllegalArgumentException e2) {
            return null;
        }
    }
}
