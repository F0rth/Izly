package defpackage;

public abstract class ha extends aa {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected final void a(fr.smoney.android.izly.data.model.GetContactDetailsData r17, android.view.View r18, android.widget.LinearLayout r19) {
        /*
        r16 = this;
        r5 = 0;
        r0 = r17;
        r2 = r0.H;
        if (r2 == 0) goto L_0x00fe;
    L_0x0007:
        r0 = r17;
        r2 = r0.H;
        r2 = r2.size();
        if (r2 <= 0) goto L_0x00fe;
    L_0x0011:
        r2 = r16.i();
        r2 = r2.b;
        r2 = r2.j;
        r2 = java.util.Currency.getInstance(r2);
        r6 = r2.getSymbol();
        r0 = r16;
        r2 = r0.d;
        r7 = r2.getLayoutInflater();
        r0 = r17;
        r2 = r0.H;
        r8 = r2.iterator();
        r2 = 0;
        r5 = r2;
    L_0x0033:
        r2 = r8.hasNext();
        if (r2 == 0) goto L_0x00fe;
    L_0x0039:
        r2 = r8.next();
        r2 = (fr.smoney.android.izly.data.model.Transaction) r2;
        r3 = r2.n;
        if (r3 == 0) goto L_0x004d;
    L_0x0043:
        r3 = r2.n;
        r4 = 1;
        if (r3 == r4) goto L_0x004d;
    L_0x0048:
        r3 = r2.n;
        r4 = 2;
        if (r3 != r4) goto L_0x0108;
    L_0x004d:
        r3 = 2130903140; // 0x7f030064 float:1.741309E38 double:1.052806036E-314;
        r4 = 0;
        r0 = r19;
        r9 = r7.inflate(r3, r0, r4);
        r3 = 2131755390; // 0x7f10017e float:1.9141658E38 double:1.0532271035E-314;
        r3 = r9.findViewById(r3);
        r3 = (android.widget.TextView) r3;
        r10 = r2.k;
        r4 = defpackage.jk.a(r10);
        r3.setText(r4);
        r3 = 2131755391; // 0x7f10017f float:1.914166E38 double:1.053227104E-314;
        r3 = r9.findViewById(r3);
        r3 = (android.widget.TextView) r3;
        r4 = r2.n;
        switch(r4) {
            case 0: goto L_0x009f;
            case 1: goto L_0x00bf;
            case 2: goto L_0x00df;
            default: goto L_0x0077;
        };
    L_0x0077:
        r4 = "+";
    L_0x0079:
        r10 = "%1$.2f%2$s";
        r11 = 2;
        r11 = new java.lang.Object[r11];
        r12 = 0;
        r14 = r2.g;
        r2 = java.lang.Double.valueOf(r14);
        r11[r12] = r2;
        r2 = 1;
        r11[r2] = r6;
        r2 = java.lang.String.format(r10, r11);
        r2 = r4.concat(r2);
        r3.setText(r2);
        r0 = r19;
        r0.addView(r9);
        if (r5 != 0) goto L_0x0108;
    L_0x009c:
        r2 = 1;
    L_0x009d:
        r5 = r2;
        goto L_0x0033;
    L_0x009f:
        r4 = r16.getResources();
        r10 = 2131689629; // 0x7f0f009d float:1.9008279E38 double:1.0531946133E-314;
        r4 = r4.getColor(r10);
        r3.setTextColor(r4);
        r4 = 2131755389; // 0x7f10017d float:1.9141656E38 double:1.053227103E-314;
        r4 = r9.findViewById(r4);
        r4 = (android.widget.TextView) r4;
        r10 = 2131231383; // 0x7f080297 float:1.8078845E38 double:1.0529682097E-314;
        r4.setText(r10);
        r4 = "-";
        goto L_0x0079;
    L_0x00bf:
        r4 = r16.getResources();
        r10 = 2131689629; // 0x7f0f009d float:1.9008279E38 double:1.0531946133E-314;
        r4 = r4.getColor(r10);
        r3.setTextColor(r4);
        r4 = 2131755389; // 0x7f10017d float:1.9141656E38 double:1.053227103E-314;
        r4 = r9.findViewById(r4);
        r4 = (android.widget.TextView) r4;
        r10 = 2131231385; // 0x7f080299 float:1.807885E38 double:1.0529682107E-314;
        r4.setText(r10);
        r4 = "-";
        goto L_0x0079;
    L_0x00df:
        r4 = r16.getResources();
        r10 = 2131689620; // 0x7f0f0094 float:1.900826E38 double:1.053194609E-314;
        r4 = r4.getColor(r10);
        r3.setTextColor(r4);
        r4 = 2131755389; // 0x7f10017d float:1.9141656E38 double:1.053227103E-314;
        r4 = r9.findViewById(r4);
        r4 = (android.widget.TextView) r4;
        r10 = 2131231384; // 0x7f080298 float:1.8078847E38 double:1.05296821E-314;
        r4.setText(r10);
        goto L_0x0077;
    L_0x00fe:
        if (r5 != 0) goto L_0x0107;
    L_0x0100:
        r2 = 8;
        r0 = r18;
        r0.setVisibility(r2);
    L_0x0107:
        return;
    L_0x0108:
        r2 = r5;
        goto L_0x009d;
        */
        throw new UnsupportedOperationException("Method not decompiled: ha.a(fr.smoney.android.izly.data.model.GetContactDetailsData, android.view.View, android.widget.LinearLayout):void");
    }
}
