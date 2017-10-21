package com.joanzapata.android.iconify;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;
import java.io.IOException;

public final class Iconify {
    public static final String TAG = Iconify.class.getSimpleName();
    private static final String TTF_FILE = "fontawesome-webfont-4.3.0.ttf";
    private static Typeface typeface = null;

    private Iconify() {
    }

    public static final void addIcons(TextView... textViewArr) {
        for (TextView textView : textViewArr) {
            textView.setTypeface(getTypeface(textView.getContext()));
            textView.setText(compute(textView.getText()));
        }
    }

    public static CharSequence compute(CharSequence charSequence) {
        return charSequence instanceof Spanned ? Html.fromHtml(Utils.replaceIcons(new StringBuilder(Html.toHtml((Spanned) charSequence))).toString()) : Utils.replaceIcons(new StringBuilder(charSequence.toString()));
    }

    public static final Typeface getTypeface(Context context) {
        if (typeface == null) {
            try {
                typeface = Typeface.createFromFile(Utils.resourceToFile(context, TTF_FILE));
            } catch (IOException e) {
                return null;
            }
        }
        return typeface;
    }

    public static final void setIcon(TextView textView, IconValue iconValue) {
        textView.setTypeface(getTypeface(textView.getContext()));
        textView.setText(String.valueOf(iconValue.character));
    }
}
