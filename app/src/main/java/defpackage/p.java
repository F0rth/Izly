package defpackage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.GripView;
import scanpay.it.SPCreditCard;

public final class p {
    private final Context a;

    public p(Context context) {
        this.a = context;
    }

    private double a(float f) {
        return ((double) (this.a.getResources().getDisplayMetrics().density * f)) + 0.5d;
    }

    public final View a(boolean z, SPCreditCard sPCreditCard, int[] iArr) {
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        View linearLayout = new LinearLayout(this.a);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setId(20);
        linearLayout.setOrientation(1);
        linearLayout.setGravity(17);
        linearLayout.setBackgroundColor(Color.parseColor("#F3F3F3"));
        View scrollView = new ScrollView(this.a);
        LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, 10);
        layoutParams2.weight = GripView.DEFAULT_DOT_SIZE_RADIUS_DP;
        scrollView.setId(21);
        scrollView.setLayoutParams(layoutParams2);
        View linearLayout2 = new LinearLayout(this.a);
        linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout2.setOrientation(1);
        linearLayout2.setGravity(1);
        View linearLayout3 = new LinearLayout(this.a);
        LayoutParams layoutParams3 = new LinearLayout.LayoutParams((int) a(298.0f), (int) a(204.0f));
        layoutParams3.setMargins(0, (int) a(20.0f), 0, 0);
        linearLayout3.setLayoutParams(layoutParams3);
        linearLayout3.setId(25);
        linearLayout3.setOrientation(1);
        linearLayout3.setGravity(17);
        View relativeLayout = new RelativeLayout(this.a);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams((int) a(280.0f), (int) a(185.0f)));
        View imageView = new ImageView(this.a);
        LayoutParams layoutParams4 = new RelativeLayout.LayoutParams((int) a(280.0f), (int) a(185.0f));
        layoutParams4.addRule(14);
        imageView.setId(22);
        imageView.setLayoutParams(layoutParams4);
        imageView.setScaleType(ScaleType.FIT_XY);
        relativeLayout.addView(imageView);
        if (z) {
            imageView.setVisibility(4);
        } else if (sPCreditCard.b == ou.AMERICAN_EXPRESS) {
            r8 = ((double) iArr[0]) / 445.0d;
            r10 = ((double) iArr[1]) / 276.0d;
            r7 = new M(this.a);
            r12 = new RelativeLayout.LayoutParams((int) a(50.0f), (int) a(22.0f));
            r12.addRule(10);
            r12.addRule(9);
            r12.setMargins((int) a((float) ((r8 * 280.0d) - 4.0d)), (int) a((float) ((r10 * 175.0d) - 20.0d)), 0, 0);
            r7.setLayoutParams(r12);
            r7.setText(sPCreditCard.a.substring(0, 4));
            relativeLayout.addView(r7);
            r8 = ((double) iArr[2]) / 445.0d;
            r10 = ((double) iArr[3]) / 276.0d;
            r7 = new M(this.a);
            r12 = new RelativeLayout.LayoutParams((int) a(88.0f), (int) a(22.0f));
            r12.addRule(10);
            r12.addRule(9);
            r12.setMargins((int) a((float) ((r8 * 280.0d) - 4.0d)), (int) a((float) ((r10 * 175.0d) - 20.0d)), 0, 0);
            r7.setLayoutParams(r12);
            r7.setText(sPCreditCard.a.substring(4, 10));
            relativeLayout.addView(r7);
            r8 = ((double) iArr[4]) / 445.0d;
            r10 = ((double) iArr[5]) / 276.0d;
            r7 = new M(this.a);
            r12 = new RelativeLayout.LayoutParams((int) a(63.0f), (int) a(22.0f));
            r12.addRule(10);
            r12.addRule(9);
            r12.setMargins((int) a((float) ((r8 * 280.0d) - 4.0d)), (int) a((float) ((r10 * 175.0d) - 20.0d)), 0, 0);
            r7.setLayoutParams(r12);
            r7.setText(sPCreditCard.a.substring(10, 15));
            relativeLayout.addView(r7);
        } else {
            r8 = ((double) iArr[0]) / 445.0d;
            r10 = ((double) iArr[1]) / 276.0d;
            r7 = new M(this.a);
            r12 = new RelativeLayout.LayoutParams((int) a(50.0f), (int) a(22.0f));
            r12.addRule(10);
            r12.addRule(9);
            r12.setMargins((int) a((float) ((r8 * 280.0d) - 4.0d)), (int) a((float) ((r10 * 175.0d) - 20.0d)), 0, 0);
            r7.setLayoutParams(r12);
            r7.setText(sPCreditCard.a.substring(0, 4));
            relativeLayout.addView(r7);
            r8 = ((double) iArr[2]) / 445.0d;
            r10 = ((double) iArr[3]) / 276.0d;
            r7 = new M(this.a);
            r12 = new RelativeLayout.LayoutParams((int) a(50.0f), (int) a(22.0f));
            r12.addRule(10);
            r12.addRule(9);
            r12.setMargins((int) a((float) ((r8 * 280.0d) - 4.0d)), (int) a((float) ((r10 * 175.0d) - 20.0d)), 0, 0);
            r7.setLayoutParams(r12);
            r7.setText(sPCreditCard.a.substring(4, 8));
            relativeLayout.addView(r7);
            r8 = ((double) iArr[4]) / 445.0d;
            r10 = ((double) iArr[5]) / 276.0d;
            r7 = new M(this.a);
            r12 = new RelativeLayout.LayoutParams((int) a(50.0f), (int) a(22.0f));
            r12.addRule(10);
            r12.addRule(9);
            r12.setMargins((int) a((float) ((r8 * 280.0d) - 4.0d)), (int) a((float) ((r10 * 175.0d) - 20.0d)), 0, 0);
            r7.setLayoutParams(r12);
            r7.setText(sPCreditCard.a.substring(8, 12));
            relativeLayout.addView(r7);
            r8 = ((double) iArr[6]) / 445.0d;
            r10 = ((double) iArr[7]) / 276.0d;
            r7 = new M(this.a);
            r12 = new RelativeLayout.LayoutParams((int) a(50.0f), (int) a(22.0f));
            r12.addRule(10);
            r12.addRule(9);
            r12.setMargins((int) a((float) ((r8 * 280.0d) - 4.0d)), (int) a((float) ((r10 * 175.0d) - 20.0d)), 0, 0);
            r7.setLayoutParams(r12);
            r7.setText(sPCreditCard.a.substring(12, 16));
            relativeLayout.addView(r7);
        }
        linearLayout3.addView(relativeLayout);
        linearLayout2.addView(linearLayout3);
        linearLayout2.addView(new R(this.a));
        scrollView.addView(linearLayout2);
        scrollView.setFocusable(false);
        scrollView.setFocusableInTouchMode(false);
        linearLayout.addView(scrollView);
        Drawable gradientDrawable = new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{-4473925, -2236963});
        linearLayout2 = new LinearLayout(this.a);
        linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        if (VERSION.SDK_INT >= 16) {
            linearLayout2.setBackground(gradientDrawable);
        } else {
            linearLayout2.setBackgroundDrawable(gradientDrawable);
        }
        linearLayout2.setGravity(21);
        linearLayout2.setOrientation(0);
        scrollView = new Button(this.a);
        LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-2, (int) a(40.0f));
        layoutParams5.setMargins(5, 2, 5, 2);
        scrollView.setLayoutParams(layoutParams5);
        scrollView.setGravity(17);
        scrollView.setEnabled(false);
        scrollView.setId(24);
        scrollView.setText(k.a().a("Confirmation"));
        scrollView.setTextSize(14.0f);
        scrollView.setPadding(20, 0, 20, 0);
        linearLayout2.addView(scrollView);
        linearLayout.addView(linearLayout2);
        return linearLayout;
    }
}
