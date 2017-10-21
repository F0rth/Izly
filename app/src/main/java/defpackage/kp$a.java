package defpackage;

import android.os.Build;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

enum kp$a {
    X86_32,
    X86_64,
    ARM_UNKNOWN,
    PPC,
    PPC64,
    ARMV6,
    ARMV7,
    UNKNOWN,
    ARMV7S,
    ARM64;
    
    private static final Map<String, kp$a> k = null;

    static {
        Map hashMap = new HashMap(4);
        k = hashMap;
        hashMap.put("armeabi-v7a", ARMV7);
        k.put("armeabi", ARMV6);
        k.put("arm64-v8a", ARM64);
        k.put("x86", X86_32);
    }

    static kp$a a() {
        Object obj = Build.CPU_ABI;
        if (TextUtils.isEmpty(obj)) {
            js.a().a("Fabric", "Architecture#getValue()::Build.CPU_ABI returned null or empty");
            return UNKNOWN;
        }
        kp$a kp_a = (kp$a) k.get(obj.toLowerCase(Locale.US));
        return kp_a == null ? UNKNOWN : kp_a;
    }
}
