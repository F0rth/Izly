package com.devspark.robototextview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

public class RobotoTypefaceManager {
    private static final int ROBOTOSLAB_BOLD = 19;
    private static final int ROBOTOSLAB_LIGHT = 17;
    private static final int ROBOTOSLAB_REGULAR = 18;
    private static final int ROBOTOSLAB_THIN = 16;
    private static final int ROBOTO_BLACK = 10;
    private static final int ROBOTO_BLACK_ITALIC = 11;
    private static final int ROBOTO_BOLD = 8;
    private static final int ROBOTO_BOLD_ITALIC = 9;
    private static final int ROBOTO_CONDENSED = 12;
    private static final int ROBOTO_CONDENSED_BOLD = 14;
    private static final int ROBOTO_CONDENSED_BOLD_ITALIC = 15;
    private static final int ROBOTO_CONDENSED_ITALIC = 13;
    private static final int ROBOTO_ITALIC = 5;
    private static final int ROBOTO_LIGHT = 2;
    private static final int ROBOTO_LIGHT_ITALIC = 3;
    private static final int ROBOTO_MEDIUM = 6;
    private static final int ROBOTO_MEDIUM_ITALIC = 7;
    private static final int ROBOTO_REGULAR = 4;
    private static final int ROBOTO_THIN = 0;
    private static final int ROBOTO_THIN_ITALIC = 1;
    private static final SparseArray<Typeface> mTypefaces = new SparseArray(20);

    private static Typeface createTypeface(Context context, int i) throws IllegalArgumentException {
        switch (i) {
            case 0:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
            case 1:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-ThinItalic.ttf");
            case 2:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
            case 3:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-LightItalic.ttf");
            case 4:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
            case 5:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Italic.ttf");
            case 6:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
            case 7:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-MediumItalic.ttf");
            case 8:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
            case 9:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-BoldItalic.ttf");
            case 10:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Black.ttf");
            case 11:
                return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-BlackItalic.ttf");
            case 12:
                return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Regular.ttf");
            case 13:
                return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Italic.ttf");
            case 14:
                return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Bold.ttf");
            case 15:
                return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-BoldItalic.ttf");
            case 16:
                return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoSlab-Thin.ttf");
            case 17:
                return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoSlab-Light.ttf");
            case 18:
                return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoSlab-Regular.ttf");
            case 19:
                return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoSlab-Bold.ttf");
            default:
                throw new IllegalArgumentException("Unknown `typeface` attribute value " + i);
        }
    }

    public static Typeface obtaintTypeface(Context context, int i) throws IllegalArgumentException {
        Typeface typeface = (Typeface) mTypefaces.get(i);
        if (typeface != null) {
            return typeface;
        }
        typeface = createTypeface(context, i);
        mTypefaces.put(i, typeface);
        return typeface;
    }
}
