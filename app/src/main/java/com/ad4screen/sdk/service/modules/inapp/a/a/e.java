package com.ad4screen.sdk.service.modules.inapp.a.a;

import java.util.Calendar;

public enum e {
    UNKNOWN,
    MO,
    TU,
    WE,
    TH,
    FR,
    SA,
    SU;

    public static e a(Calendar calendar) {
        switch (calendar.get(7)) {
            case 1:
                return SU;
            case 2:
                return MO;
            case 3:
                return TU;
            case 4:
                return WE;
            case 5:
                return TH;
            case 6:
                return FR;
            case 7:
                return SA;
            default:
                return UNKNOWN;
        }
    }
}
