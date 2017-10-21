package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class SMSMMSResultParser extends ResultParser {
    private static void addNumberVia(Collection<String> collection, Collection<String> collection2, String str) {
        Object obj = null;
        int indexOf = str.indexOf(59);
        if (indexOf < 0) {
            collection.add(str);
            collection2.add(null);
            return;
        }
        collection.add(str.substring(0, indexOf));
        String substring = str.substring(indexOf + 1);
        if (substring.startsWith("via=")) {
            obj = substring.substring(4);
        }
        collection2.add(obj);
    }

    public final SMSParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("sms:") && !massagedText.startsWith("SMS:") && !massagedText.startsWith("mms:") && !massagedText.startsWith("MMS:")) {
            return null;
        }
        String str;
        String str2;
        int i;
        Map parseNameValuePairs = ResultParser.parseNameValuePairs(massagedText);
        if (parseNameValuePairs == null || parseNameValuePairs.isEmpty()) {
            str = null;
            str2 = null;
            i = 0;
        } else {
            str = (String) parseNameValuePairs.get("subject");
            str2 = (String) parseNameValuePairs.get("body");
            i = 1;
        }
        int indexOf = massagedText.indexOf(63, 4);
        String substring = (indexOf < 0 || i == 0) ? massagedText.substring(4) : massagedText.substring(4, indexOf);
        indexOf = -1;
        List arrayList = new ArrayList(1);
        List arrayList2 = new ArrayList(1);
        while (true) {
            int indexOf2 = substring.indexOf(44, indexOf + 1);
            if (indexOf2 > indexOf) {
                addNumberVia(arrayList, arrayList2, substring.substring(indexOf + 1, indexOf2));
                indexOf = indexOf2;
            } else {
                addNumberVia(arrayList, arrayList2, substring.substring(indexOf + 1));
                return new SMSParsedResult((String[]) arrayList.toArray(new String[arrayList.size()]), (String[]) arrayList2.toArray(new String[arrayList2.size()]), str, str2);
            }
        }
    }
}
