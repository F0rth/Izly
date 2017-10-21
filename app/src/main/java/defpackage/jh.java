package defpackage;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import fr.smoney.android.izly.R;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public final class jh {
    private static final Pattern a = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+\\']{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+");

    public static Spannable a(CharSequence charSequence, int i, ForegroundColorSpan foregroundColorSpan) {
        Spannable spannableString = new SpannableString(charSequence);
        spannableString.setSpan(foregroundColorSpan, 0, 2, 33);
        return spannableString;
    }

    public static String a(Context context, double d, double d2, boolean z) {
        String concat = new DecimalFormat("##.#").format(((d / d2) - 1.0d) * 100.0d).concat("%");
        if (z) {
            return context.getString(R.string.confirm_result_tv_commission_info_deducted, new Object[]{String.valueOf(d2), String.valueOf(d), concat});
        }
        return context.getString(R.string.confirm_result_tv_commission_info_to_deduct, new Object[]{String.valueOf(d2), String.valueOf(d), concat});
    }

    public static final void a() {
    }

    public static final boolean a(CharSequence charSequence) {
        return charSequence == null ? false : Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    public static boolean a(String str) {
        return TextUtils.isEmpty(str) ? false : "AEIOUY".contains(str.subSequence(0, 1).toString().toUpperCase(Locale.getDefault()));
    }

    public static String b(String str) {
        String toUpperCase = str.substring(0, 1).toUpperCase(Locale.getDefault());
        return !Character.isLetter(toUpperCase.charAt(0)) ? "#" : toUpperCase;
    }

    public static final boolean b(CharSequence charSequence) {
        return charSequence == null ? false : a.matcher(charSequence).matches();
    }

    public static String c(String str) {
        if (str == null) {
            return "";
        }
        String trim = str.trim();
        BreakIterator characterInstance = BreakIterator.getCharacterInstance(Locale.getDefault());
        characterInstance.setText(trim);
        StringBuilder stringBuilder = new StringBuilder();
        int first = characterInstance.first();
        int next = characterInstance.next();
        while (next != -1) {
            stringBuilder.append(trim.substring(first, next));
            if (next % 4 == 0) {
                stringBuilder.append(' ');
            }
            int i = next;
            next = characterInstance.next();
            first = i;
        }
        return stringBuilder.toString().trim();
    }

    public static boolean d(String str) {
        return str == null || "".equals(str.trim());
    }
}
