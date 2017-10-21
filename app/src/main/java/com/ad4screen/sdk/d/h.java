package com.ad4screen.sdk.d;

import java.util.HashMap;
import java.util.Locale;

public final class h {

    public enum a {
        CALENDAR,
        CAMERA,
        CONTACTS,
        LOCATION,
        MICROPHONE,
        PHONE,
        SENSORS,
        SMS,
        STORAGE;

        public static a a(String str) {
            return str == null ? null : valueOf(str.toUpperCase(Locale.US));
        }
    }

    static class b {
        public static final HashMap<a, String[]> a;

        static {
            HashMap hashMap = new HashMap();
            a = hashMap;
            hashMap.put(a.CALENDAR, new String[]{"READ_CALENDAR", "WRITE_CALENDAR"});
            a.put(a.CAMERA, new String[]{"CAMERA"});
            a.put(a.CONTACTS, new String[]{"READ_CONTACTS", "WRITE_CONTACTS", "GET_ACCOUNTS"});
            a.put(a.LOCATION, new String[]{"ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION"});
            a.put(a.MICROPHONE, new String[]{"RECORD_AUDIO"});
            a.put(a.PHONE, new String[]{"READ_PHONE_STATE", "CALL_PHONE", "READ_CALL_LOG", "WRITE_CALL_LOG", "ADD_VOICEMAIL", "USE_SIP", "PROCESS_OUTGOING_CALLS"});
            a.put(a.SENSORS, new String[]{"BODY_SENSORS"});
            a.put(a.SMS, new String[]{"SEND_SMS", "RECEIVE_SMS", "READ_SMS", "RECEIVE_WAP_PUSH", "RECEIVE_MMS"});
            a.put(a.STORAGE, new String[]{"READ_EXTERNAL_STORAGE", "WRITE_EXTERNAL_STORAGE"});
        }
    }

    public static String[] a(a aVar) {
        return (String[]) b.a.get(aVar);
    }
}
