package defpackage;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class je {
    public static void a(Context context, View view) {
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void b(Context context, View view) {
        if (context != null) {
            ((InputMethodManager) context.getSystemService("input_method")).showSoftInput(view, 1);
        }
    }
}
