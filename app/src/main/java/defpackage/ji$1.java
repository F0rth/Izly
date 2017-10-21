package defpackage;

import android.text.util.Linkify.MatchFilter;

final class ji$1 implements MatchFilter {
    ji$1() {
    }

    public final boolean acceptMatch(CharSequence charSequence, int i, int i2) {
        return i == 0 || charSequence.charAt(i - 1) != '@';
    }
}
