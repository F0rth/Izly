package com.joanzapata.android.iconify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import com.joanzapata.android.iconify.Iconify.IconValue;

public class IconDrawable extends Drawable {
    public static final int ANDROID_ACTIONBAR_ICON_SIZE_DP = 24;
    private int alpha = 255;
    private final Context context;
    private final IconValue icon;
    private TextPaint paint;
    private int size = -1;

    public IconDrawable(Context context, IconValue iconValue) {
        this.context = context;
        this.icon = iconValue;
        this.paint = new TextPaint();
        this.paint.setTypeface(Iconify.getTypeface(context));
        this.paint.setStyle(Style.STROKE);
        this.paint.setTextAlign(Align.CENTER);
        this.paint.setUnderlineText(false);
        this.paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.paint.setAntiAlias(true);
    }

    public IconDrawable actionBarSize() {
        return sizeDp(24);
    }

    public IconDrawable alpha(int i) {
        setAlpha(i);
        invalidateSelf();
        return this;
    }

    public void clearColorFilter() {
        this.paint.setColorFilter(null);
    }

    public IconDrawable color(int i) {
        this.paint.setColor(i);
        invalidateSelf();
        return this;
    }

    public IconDrawable colorRes(int i) {
        this.paint.setColor(this.context.getResources().getColor(i));
        invalidateSelf();
        return this;
    }

    public void draw(Canvas canvas) {
        this.paint.setTextSize((float) getBounds().height());
        Rect rect = new Rect();
        String valueOf = String.valueOf(this.icon.character);
        this.paint.getTextBounds(valueOf, 0, 1, rect);
        float height = ((float) (getBounds().height() - rect.height())) / 2.0f;
        float height2 = (float) rect.height();
        canvas.drawText(valueOf, ((float) getBounds().width()) / 2.0f, (height + height2) - ((float) rect.bottom), this.paint);
    }

    public int getIntrinsicHeight() {
        return this.size;
    }

    public int getIntrinsicWidth() {
        return this.size;
    }

    public int getOpacity() {
        return this.alpha;
    }

    public boolean isStateful() {
        return true;
    }

    public void setAlpha(int i) {
        this.alpha = i;
        this.paint.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    public boolean setState(int[] iArr) {
        int alpha = this.paint.getAlpha();
        int i = Utils.isEnabled(iArr) ? this.alpha : this.alpha / 2;
        this.paint.setAlpha(i);
        return alpha != i;
    }

    public void setStyle(Style style) {
        this.paint.setStyle(style);
    }

    public IconDrawable sizeDp(int i) {
        return sizePx(Utils.convertDpToPx(this.context, (float) i));
    }

    public IconDrawable sizePx(int i) {
        this.size = i;
        setBounds(0, 0, i, i);
        invalidateSelf();
        return this;
    }

    public IconDrawable sizeRes(int i) {
        return sizePx(this.context.getResources().getDimensionPixelSize(i));
    }
}
