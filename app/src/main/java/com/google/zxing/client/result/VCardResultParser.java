package com.google.zxing.client.result;

import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.google.zxing.Result;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VCardResultParser extends ResultParser {
    private static final Pattern BEGIN_VCARD = Pattern.compile("BEGIN:VCARD", 2);
    private static final Pattern COMMA = Pattern.compile(",");
    private static final Pattern CR_LF_SPACE_TAB = Pattern.compile("\r\n[ \t]");
    private static final Pattern EQUALS = Pattern.compile("=");
    private static final Pattern NEWLINE_ESCAPE = Pattern.compile("\\\\[nN]");
    private static final Pattern SEMICOLON = Pattern.compile(";");
    private static final Pattern SEMICOLON_OR_COMMA = Pattern.compile("[;,]");
    private static final Pattern UNESCAPED_SEMICOLONS = Pattern.compile("(?<!\\\\);+");
    private static final Pattern VCARD_ESCAPES = Pattern.compile("\\\\([,;\\\\])");
    private static final Pattern VCARD_LIKE_DATE = Pattern.compile("\\d{4}-?\\d{2}-?\\d{2}");

    private static String decodeQuotedPrintable(CharSequence charSequence, String str) {
        int length = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            switch (charAt) {
                case '\n':
                case '\r':
                    break;
                case '=':
                    if (i >= length - 2) {
                        break;
                    }
                    charAt = charSequence.charAt(i + 1);
                    if (!(charAt == '\r' || charAt == '\n')) {
                        char charAt2 = charSequence.charAt(i + 2);
                        int parseHexDigit = ResultParser.parseHexDigit(charAt);
                        int parseHexDigit2 = ResultParser.parseHexDigit(charAt2);
                        if (parseHexDigit >= 0 && parseHexDigit2 >= 0) {
                            byteArrayOutputStream.write((parseHexDigit << 4) + parseHexDigit2);
                        }
                        i += 2;
                        break;
                    }
                default:
                    maybeAppendFragment(byteArrayOutputStream, str, stringBuilder);
                    stringBuilder.append(charAt);
                    break;
            }
            i++;
        }
        maybeAppendFragment(byteArrayOutputStream, str, stringBuilder);
        return stringBuilder.toString();
    }

    private static void formatNames(Iterable<List<String>> iterable) {
        if (iterable != null) {
            for (List list : iterable) {
                String str = (String) list.get(0);
                String[] strArr = new String[5];
                int i = 0;
                int i2 = 0;
                while (i2 < 4) {
                    int indexOf = str.indexOf(59, i);
                    if (indexOf <= 0) {
                        break;
                    }
                    strArr[i2] = str.substring(i, indexOf);
                    i2++;
                    i = indexOf + 1;
                }
                strArr[i2] = str.substring(i);
                StringBuilder stringBuilder = new StringBuilder(100);
                maybeAppendComponent(strArr, 3, stringBuilder);
                maybeAppendComponent(strArr, 1, stringBuilder);
                maybeAppendComponent(strArr, 2, stringBuilder);
                maybeAppendComponent(strArr, 0, stringBuilder);
                maybeAppendComponent(strArr, 4, stringBuilder);
                list.set(0, stringBuilder.toString().trim());
            }
        }
    }

    private static boolean isLikeVCardDate(CharSequence charSequence) {
        return charSequence == null || VCARD_LIKE_DATE.matcher(charSequence).matches();
    }

    static List<String> matchSingleVCardPrefixedField(CharSequence charSequence, String str, boolean z, boolean z2) {
        List matchVCardPrefixedField = matchVCardPrefixedField(charSequence, str, z, z2);
        return (matchVCardPrefixedField == null || matchVCardPrefixedField.isEmpty()) ? null : (List) matchVCardPrefixedField.get(0);
    }

    static List<List<String>> matchVCardPrefixedField(CharSequence charSequence, String str, boolean z, boolean z2) {
        int i = 0;
        int length = str.length();
        List<List<String>> list = null;
        while (i < length) {
            Matcher matcher = Pattern.compile("(?:^|\n)" + charSequence + "(?:;([^:]*))?:", 2).matcher(str);
            if (i > 0) {
                i--;
            }
            if (!matcher.find(i)) {
                break;
            }
            String str2;
            Object obj;
            List list2;
            int end = matcher.end(0);
            CharSequence group = matcher.group(1);
            List list3 = null;
            String str3 = null;
            if (group != null) {
                String[] split = SEMICOLON.split(group);
                int length2 = split.length;
                Object obj2 = null;
                int i2 = 0;
                while (i2 < length2) {
                    Object obj3;
                    group = split[i2];
                    if (list3 == null) {
                        list3 = new ArrayList(1);
                    }
                    list3.add(group);
                    String[] split2 = EQUALS.split(group, 2);
                    if (split2.length > 1) {
                        String str4 = split2[0];
                        str2 = split2[1];
                        if ("ENCODING".equalsIgnoreCase(str4) && "QUOTED-PRINTABLE".equalsIgnoreCase(str2)) {
                            obj3 = 1;
                            i2++;
                            obj2 = obj3;
                        } else if ("CHARSET".equalsIgnoreCase(str4)) {
                            str3 = str2;
                            obj3 = obj2;
                            i2++;
                            obj2 = obj3;
                        }
                    }
                    obj3 = obj2;
                    i2++;
                    obj2 = obj3;
                }
                str2 = str3;
                obj = obj2;
                list2 = list3;
            } else {
                str2 = null;
                obj = null;
                list2 = null;
            }
            i = end;
            while (true) {
                i = str.indexOf(10, i);
                if (i < 0) {
                    break;
                } else if (i >= str.length() - 1 || (str.charAt(i + 1) != ' ' && str.charAt(i + 1) != '\t')) {
                    if (obj == null || ((i <= 0 || str.charAt(i - 1) != '=') && (i < 2 || str.charAt(i - 2) != '='))) {
                        break;
                    }
                    i++;
                } else {
                    i += 2;
                }
            }
            if (i < 0) {
                i = length;
            } else if (i > end) {
                Object decodeQuotedPrintable;
                if (list == null) {
                    list = new ArrayList(1);
                }
                if (i > 0 && str.charAt(i - 1) == '\r') {
                    i--;
                }
                CharSequence substring = str.substring(end, i);
                if (z) {
                    substring = substring.trim();
                }
                if (obj != null) {
                    decodeQuotedPrintable = decodeQuotedPrintable(substring, str2);
                    if (z2) {
                        decodeQuotedPrintable = UNESCAPED_SEMICOLONS.matcher(decodeQuotedPrintable).replaceAll("\n").trim();
                    }
                } else {
                    if (z2) {
                        substring = UNESCAPED_SEMICOLONS.matcher(substring).replaceAll("\n").trim();
                    }
                    decodeQuotedPrintable = VCARD_ESCAPES.matcher(NEWLINE_ESCAPE.matcher(CR_LF_SPACE_TAB.matcher(substring).replaceAll("")).replaceAll("\n")).replaceAll("$1");
                }
                if (list2 == null) {
                    List arrayList = new ArrayList(1);
                    arrayList.add(decodeQuotedPrintable);
                    list.add(arrayList);
                } else {
                    list2.add(0, decodeQuotedPrintable);
                    list.add(list2);
                }
                i++;
            } else {
                i++;
            }
        }
        return list;
    }

    private static void maybeAppendComponent(String[] strArr, int i, StringBuilder stringBuilder) {
        if (strArr[i] != null) {
            stringBuilder.append(' ');
            stringBuilder.append(strArr[i]);
        }
    }

    private static void maybeAppendFragment(ByteArrayOutputStream byteArrayOutputStream, String str, StringBuilder stringBuilder) {
        if (byteArrayOutputStream.size() > 0) {
            String str2;
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            if (str == null) {
                str2 = new String(toByteArray);
            } else {
                try {
                    str2 = new String(toByteArray, str);
                } catch (UnsupportedEncodingException e) {
                    str2 = new String(toByteArray);
                }
            }
            byteArrayOutputStream.reset();
            stringBuilder.append(str2);
        }
    }

    private static String toPrimaryValue(List<String> list) {
        return (list == null || list.isEmpty()) ? null : (String) list.get(0);
    }

    private static String[] toPrimaryValues(Collection<List<String>> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        List arrayList = new ArrayList(collection.size());
        for (List list : collection) {
            String str = (String) list.get(0);
            if (str != null && str.length() > 0) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[collection.size()]);
    }

    private static String[] toTypes(Collection<List<String>> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        List arrayList = new ArrayList(collection.size());
        for (List list : collection) {
            Object obj;
            int i = 1;
            while (i < list.size()) {
                obj = (String) list.get(i);
                int indexOf = obj.indexOf(61);
                if (indexOf < 0) {
                    break;
                } else if ("TYPE".equalsIgnoreCase(obj.substring(0, indexOf))) {
                    obj = obj.substring(indexOf + 1);
                    break;
                } else {
                    i++;
                }
            }
            obj = null;
            arrayList.add(obj);
        }
        return (String[]) arrayList.toArray(new String[collection.size()]);
    }

    public final AddressBookParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        Matcher matcher = BEGIN_VCARD.matcher(massagedText);
        if (!matcher.find() || matcher.start() != 0) {
            return null;
        }
        List matchVCardPrefixedField = matchVCardPrefixedField("FN", massagedText, true, false);
        if (matchVCardPrefixedField == null) {
            matchVCardPrefixedField = matchVCardPrefixedField(InteractionsApi.B4SCONNECTION_NOCONNECTION, massagedText, true, false);
            formatNames(matchVCardPrefixedField);
        }
        List list = matchVCardPrefixedField;
        matchVCardPrefixedField = matchSingleVCardPrefixedField("NICKNAME", massagedText, true, false);
        String[] split = matchVCardPrefixedField == null ? null : COMMA.split((CharSequence) matchVCardPrefixedField.get(0));
        Collection matchVCardPrefixedField2 = matchVCardPrefixedField("TEL", massagedText, true, false);
        Collection matchVCardPrefixedField3 = matchVCardPrefixedField("EMAIL", massagedText, true, false);
        List matchSingleVCardPrefixedField = matchSingleVCardPrefixedField("NOTE", massagedText, false, false);
        Collection matchVCardPrefixedField4 = matchVCardPrefixedField("ADR", massagedText, true, true);
        List matchSingleVCardPrefixedField2 = matchSingleVCardPrefixedField("ORG", massagedText, true, true);
        List matchSingleVCardPrefixedField3 = matchSingleVCardPrefixedField("BDAY", massagedText, true, false);
        List list2 = (matchSingleVCardPrefixedField3 == null || isLikeVCardDate((CharSequence) matchSingleVCardPrefixedField3.get(0))) ? matchSingleVCardPrefixedField3 : null;
        List matchSingleVCardPrefixedField4 = matchSingleVCardPrefixedField("TITLE", massagedText, true, false);
        Collection matchVCardPrefixedField5 = matchVCardPrefixedField("URL", massagedText, true, false);
        List matchSingleVCardPrefixedField5 = matchSingleVCardPrefixedField("IMPP", massagedText, true, false);
        matchVCardPrefixedField = matchSingleVCardPrefixedField("GEO", massagedText, true, false);
        String[] split2 = matchVCardPrefixedField == null ? null : SEMICOLON_OR_COMMA.split((CharSequence) matchVCardPrefixedField.get(0));
        if (!(split2 == null || split2.length == 2)) {
            split2 = null;
        }
        return new AddressBookParsedResult(toPrimaryValues(list), split, null, toPrimaryValues(matchVCardPrefixedField2), toTypes(matchVCardPrefixedField2), toPrimaryValues(matchVCardPrefixedField3), toTypes(matchVCardPrefixedField3), toPrimaryValue(matchSingleVCardPrefixedField5), toPrimaryValue(matchSingleVCardPrefixedField), toPrimaryValues(matchVCardPrefixedField4), toTypes(matchVCardPrefixedField4), toPrimaryValue(matchSingleVCardPrefixedField2), toPrimaryValue(list2), toPrimaryValue(matchSingleVCardPrefixedField4), toPrimaryValues(matchVCardPrefixedField5), split2);
    }
}
