package com.foxykeep.datadroid.provider.util;

public class DatabaseUtil {
    public static String getCreateIndexString(String str, String str2) {
        return "create index " + str.toLowerCase() + '_' + str2 + " on " + str + " (" + str2 + ");";
    }
}
