package com.thewingitapp.thirdparties.wingitlib.util;

public class PermissionUtil {
    public static boolean verifyPermissions(int[] iArr) {
        if (iArr.length <= 0) {
            return false;
        }
        for (int i : iArr) {
            if (i == 0) {
                return true;
            }
        }
        return false;
    }
}
