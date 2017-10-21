package org.ksoap2.serialization;

public class SoapPrimitive extends AttributeContainer {
    String name;
    String namespace;
    String value;

    public SoapPrimitive(String str, String str2, String str3) {
        this.namespace = str;
        this.name = str2;
        this.value = str3;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
        r4 = this;
        r1 = 1;
        r0 = 0;
        r2 = r5 instanceof org.ksoap2.serialization.SoapPrimitive;
        if (r2 != 0) goto L_0x0007;
    L_0x0006:
        return r0;
    L_0x0007:
        r5 = (org.ksoap2.serialization.SoapPrimitive) r5;
        r2 = r4.name;
        r3 = r5.name;
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x0038;
    L_0x0013:
        r2 = r4.namespace;
        if (r2 != 0) goto L_0x002e;
    L_0x0017:
        r2 = r5.namespace;
        if (r2 != 0) goto L_0x0038;
    L_0x001b:
        r2 = r4.value;
        if (r2 != 0) goto L_0x003a;
    L_0x001f:
        r2 = r5.value;
        if (r2 != 0) goto L_0x0038;
    L_0x0023:
        r2 = r1;
    L_0x0024:
        if (r2 == 0) goto L_0x0006;
    L_0x0026:
        r2 = r4.attributesAreEqual(r5);
        if (r2 == 0) goto L_0x0006;
    L_0x002c:
        r0 = r1;
        goto L_0x0006;
    L_0x002e:
        r2 = r4.namespace;
        r3 = r5.namespace;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x001b;
    L_0x0038:
        r2 = r0;
        goto L_0x0024;
    L_0x003a:
        r2 = r4.value;
        r3 = r5.value;
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x0038;
    L_0x0044:
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.ksoap2.serialization.SoapPrimitive.equals(java.lang.Object):boolean");
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public int hashCode() {
        return (this.namespace == null ? 0 : this.namespace.hashCode()) ^ this.name.hashCode();
    }

    public String toString() {
        return this.value;
    }
}
