package com.ad4screen.sdk.service.modules.inapp.a.b.b;

import java.util.regex.Pattern;

public class c extends a {
    private Pattern c;

    public c(Long l, String str) {
        super(l, str);
    }

    public Pattern c() {
        if (this.c == null) {
            this.c = Pattern.compile(this.b, 2);
        }
        return this.c;
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.events.RegexEvent";
    }
}
