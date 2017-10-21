package org.kobjects.util;

public class Csv {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String[] decode(java.lang.String r11) {
        /*
        r10 = 94;
        r9 = 44;
        r8 = 32;
        r2 = 0;
        r7 = 34;
        r3 = new java.util.Vector;
        r3.<init>();
        r4 = r11.length();
        r0 = r2;
    L_0x0013:
        if (r0 >= r4) goto L_0x001e;
    L_0x0015:
        r1 = r11.charAt(r0);
        if (r1 > r8) goto L_0x001e;
    L_0x001b:
        r0 = r0 + 1;
        goto L_0x0013;
    L_0x001e:
        if (r0 >= r4) goto L_0x00a5;
    L_0x0020:
        r1 = r11.charAt(r0);
        if (r1 != r7) goto L_0x0093;
    L_0x0026:
        r0 = r0 + 1;
        r5 = new java.lang.StringBuffer;
        r5.<init>();
    L_0x002d:
        r1 = r0 + 1;
        r6 = r11.charAt(r0);
        if (r6 != r10) goto L_0x0047;
    L_0x0035:
        if (r1 >= r4) goto L_0x0047;
    L_0x0037:
        r0 = r1 + 1;
        r1 = r11.charAt(r1);
        if (r1 != r10) goto L_0x0043;
    L_0x003f:
        r5.append(r1);
        goto L_0x002d;
    L_0x0043:
        r1 = r1 + -64;
        r1 = (char) r1;
        goto L_0x003f;
    L_0x0047:
        if (r6 != r7) goto L_0x00ca;
    L_0x0049:
        if (r1 == r4) goto L_0x0057;
    L_0x004b:
        r0 = r11.charAt(r1);
        if (r0 != r7) goto L_0x0057;
    L_0x0051:
        r0 = r1 + 1;
    L_0x0053:
        r5.append(r6);
        goto L_0x002d;
    L_0x0057:
        r0 = r5.toString();
        r3.addElement(r0);
    L_0x005e:
        if (r1 >= r4) goto L_0x0069;
    L_0x0060:
        r0 = r11.charAt(r1);
        if (r0 > r8) goto L_0x0069;
    L_0x0066:
        r1 = r1 + 1;
        goto L_0x005e;
    L_0x0069:
        if (r1 >= r4) goto L_0x00a5;
    L_0x006b:
        r0 = r11.charAt(r1);
        if (r0 == r9) goto L_0x0090;
    L_0x0071:
        r0 = new java.lang.RuntimeException;
        r2 = new java.lang.StringBuilder;
        r3 = "Comma expected at ";
        r2.<init>(r3);
        r1 = r2.append(r1);
        r2 = " line: ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x0090:
        r0 = r1 + 1;
        goto L_0x0013;
    L_0x0093:
        r1 = r11.indexOf(r9, r0);
        r5 = -1;
        if (r1 != r5) goto L_0x00bb;
    L_0x009a:
        r0 = r11.substring(r0);
        r0 = r0.trim();
        r3.addElement(r0);
    L_0x00a5:
        r0 = r3.size();
        r4 = new java.lang.String[r0];
        r1 = r2;
    L_0x00ac:
        r0 = r4.length;
        if (r1 >= r0) goto L_0x00cc;
    L_0x00af:
        r0 = r3.elementAt(r1);
        r0 = (java.lang.String) r0;
        r4[r1] = r0;
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x00ac;
    L_0x00bb:
        r0 = r11.substring(r0, r1);
        r0 = r0.trim();
        r3.addElement(r0);
        r0 = r1 + 1;
        goto L_0x0013;
    L_0x00ca:
        r0 = r1;
        goto L_0x0053;
    L_0x00cc:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.util.Csv.decode(java.lang.String):java.lang.String[]");
    }

    public static String encode(String str, char c) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == c || charAt == '^') {
                stringBuffer.append(charAt);
                stringBuffer.append(charAt);
            } else if (charAt < ' ') {
                stringBuffer.append('^');
                stringBuffer.append((char) (charAt + 64));
            } else {
                stringBuffer.append(charAt);
            }
        }
        return stringBuffer.toString();
    }

    public static String encode(Object[] objArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < objArr.length; i++) {
            if (i != 0) {
                stringBuffer.append(',');
            }
            Object obj = objArr[i];
            if ((obj instanceof Number) || (obj instanceof Boolean)) {
                stringBuffer.append(obj.toString());
            } else {
                stringBuffer.append('\"');
                stringBuffer.append(encode(obj.toString(), '\"'));
                stringBuffer.append('\"');
            }
        }
        return stringBuffer.toString();
    }
}
