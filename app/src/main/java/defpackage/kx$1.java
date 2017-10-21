package defpackage;

import android.content.Context;

final class kx$1 implements kg<String> {
    final /* synthetic */ kx a;

    kx$1(kx kxVar) {
        this.a = kxVar;
    }

    public final /* synthetic */ Object load(Context context) throws Exception {
        String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        return installerPackageName == null ? "" : installerPackageName;
    }
}
