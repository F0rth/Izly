package defpackage;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.NumberKeyListener;

public final class ja extends NumberKeyListener {
    private static final char[][] a = new char[][]{new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'}, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ','}, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', ','}};
    private char[] b;
    private boolean c;
    private int d;
    private final int e;

    public ja() {
        this(false, 0, 0);
    }

    public ja(boolean z, int i, int i2) {
        this.c = z;
        this.e = i2;
        if (z) {
            switch (i) {
                case 1:
                    this.d = 1;
                    break;
                case 2:
                    this.d = 2;
                    break;
                case 3:
                    this.d = 3;
                    break;
                default:
                    this.d = 0;
                    break;
            }
        }
        this.d = 0;
        this.b = a[this.d];
    }

    public final CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
        if (this.c) {
            int i5;
            char charAt;
            if (charSequence.length() == 1 && spanned.length() == 0) {
                char charAt2 = charSequence.charAt(0);
                if (charAt2 == '.' || charAt2 == ',') {
                    return "0" + charAt2;
                }
            }
            if (!(filter == null || filter.equals(""))) {
                i = 0;
                i2 = filter.length();
                charSequence = filter;
            }
            int i6 = -1;
            int length = spanned.length();
            for (i5 = 0; i5 < i3; i5++) {
                charAt = spanned.charAt(i5);
                if (charAt == '.' || charAt == ',') {
                    i6 = i5;
                }
            }
            for (i5 = i4; i5 < length; i5++) {
                charAt = spanned.charAt(i5);
                if (charAt == '.' || charAt == ',') {
                    i6 = i5;
                }
            }
            if (i6 > 0) {
                if (i4 <= i6) {
                    return null;
                }
                if (length - i6 > this.e) {
                    return "";
                }
            }
            length = i6;
            CharSequence charSequence2 = null;
            for (int i7 = i2 - 1; i7 >= i; i7--) {
                char charAt3 = charSequence.charAt(i7);
                Object obj = null;
                if (charAt3 == '.' || charAt3 == ',') {
                    if (length >= 0) {
                        obj = 1;
                    } else {
                        length = i7;
                    }
                }
                if (obj != null) {
                    if (i2 == i + 1) {
                        return "";
                    }
                    if (charSequence2 == null) {
                        charSequence2 = new SpannableStringBuilder(charSequence, i, i2);
                    }
                    charSequence2.delete(i7 - i, (i7 + 1) - i);
                }
            }
            if (charSequence2 != null) {
                return charSequence2;
            }
            if (filter == null) {
                return null;
            }
        }
        return filter;
    }

    protected final char[] getAcceptedChars() {
        return this.b;
    }

    public final int getInputType() {
        return (!this.c || this.d == 0) ? 2 : 8194;
    }
}
