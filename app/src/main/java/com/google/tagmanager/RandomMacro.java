package com.google.tagmanager;

import com.google.analytics.containertag.common.FunctionType;
import com.google.analytics.containertag.common.Key;

class RandomMacro extends FunctionCallImplementation {
    private static final String ID = FunctionType.RANDOM.toString();
    private static final String MAX = Key.MAX.toString();
    private static final String MIN = Key.MIN.toString();

    public RandomMacro() {
        super(ID, new String[0]);
    }

    public static String getFunctionId() {
        return ID;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.analytics.midtier.proto.containertag.TypeSystem.Value evaluate(java.util.Map<java.lang.String, com.google.analytics.midtier.proto.containertag.TypeSystem.Value> r7) {
        /*
        r6 = this;
        r0 = MIN;
        r0 = r7.get(r0);
        r0 = (com.google.analytics.midtier.proto.containertag.TypeSystem.Value) r0;
        r1 = MAX;
        r1 = r7.get(r1);
        r1 = (com.google.analytics.midtier.proto.containertag.TypeSystem.Value) r1;
        if (r0 == 0) goto L_0x0054;
    L_0x0012:
        r2 = com.google.tagmanager.Types.getDefaultValue();
        if (r0 == r2) goto L_0x0054;
    L_0x0018:
        if (r1 == 0) goto L_0x0054;
    L_0x001a:
        r2 = com.google.tagmanager.Types.getDefaultValue();
        if (r1 == r2) goto L_0x0054;
    L_0x0020:
        r0 = com.google.tagmanager.Types.valueToNumber(r0);
        r2 = com.google.tagmanager.Types.valueToNumber(r1);
        r1 = com.google.tagmanager.Types.getDefaultNumber();
        if (r0 == r1) goto L_0x0054;
    L_0x002e:
        r1 = com.google.tagmanager.Types.getDefaultNumber();
        if (r2 == r1) goto L_0x0054;
    L_0x0034:
        r0 = r0.doubleValue();
        r2 = r2.doubleValue();
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 > 0) goto L_0x0054;
    L_0x0040:
        r2 = r2 - r0;
        r4 = java.lang.Math.random();
        r2 = r2 * r4;
        r0 = r0 + r2;
        r0 = java.lang.Math.round(r0);
        r0 = java.lang.Long.valueOf(r0);
        r0 = com.google.tagmanager.Types.objectToValue(r0);
        return r0;
    L_0x0054:
        r2 = 4746794007244308480; // 0x41dfffffffc00000 float:NaN double:2.147483647E9;
        r0 = 0;
        goto L_0x0040;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.tagmanager.RandomMacro.evaluate(java.util.Map):com.google.analytics.midtier.proto.containertag.TypeSystem$Value");
    }

    public boolean isCacheable() {
        return false;
    }
}
