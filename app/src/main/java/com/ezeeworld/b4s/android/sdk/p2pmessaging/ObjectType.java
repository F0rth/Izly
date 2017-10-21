package com.ezeeworld.b4s.android.sdk.p2pmessaging;

public enum ObjectType {
    None("XXX", false),
    Notification("NOT", false),
    Text("TXT", false),
    Deeplink("DEP", true),
    ExternalUrl("URL", true),
    WebviewUrl("WBV", true),
    OwnerId("RID", true),
    ComplementaryData("RCD", true),
    Name("NME", true);

    private final String a;
    private final boolean b;

    private ObjectType(String str, boolean z) {
        this.a = str;
        this.b = z;
    }

    public static ObjectType fromObjectCode(String str) {
        for (ObjectType objectType : values()) {
            if (objectType.getObjectCode().equals(str)) {
                return objectType;
            }
        }
        return null;
    }

    public final String getObjectCode() {
        return this.a;
    }

    public final boolean isSystemMessage() {
        return this.b;
    }
}
