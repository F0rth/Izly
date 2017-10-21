package com.ad4screen.sdk.service.modules.inapp.a;

public enum d {
    ENTER("enter"),
    EXIT("exit");

    private String c;

    private d(String str) {
        this.c = str;
    }

    public static d a(String str) throws IllegalArgumentException {
        if (str != null) {
            str = str.trim();
            for (d dVar : values()) {
                if (str.equalsIgnoreCase(dVar.a())) {
                    return dVar;
                }
            }
        }
        throw new IllegalArgumentException("No enum with text " + str + " found");
    }

    public final String a() {
        return this.c;
    }
}
