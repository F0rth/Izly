package defpackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public final class ow extends OrientationEventListener {
    public no a;
    public char b = '\u0000';
    private final View c;
    private ox d;

    public ow(Context context) {
        super(context);
        this.c = ((Activity) context).findViewById(18);
        TextView textView = (TextView) this.c.findViewById(12);
        switch (((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation()) {
            case 1:
                this.b = (char) (this.b | 128);
                this.b = (char) (this.b | 64);
                this.d = ox.Right;
                textView.setTag(Float.valueOf(90.0f));
                return;
            case 2:
                this.b = (char) (this.b | 64);
                this.b = (char) (this.b & -129);
                this.d = ox.Bottom;
                textView.setTag(Float.valueOf(180.0f));
                return;
            case 3:
                this.b = (char) (this.b | 128);
                this.b = (char) (this.b & -65);
                this.d = ox.Left;
                textView.setTag(Float.valueOf(270.0f));
                return;
            default:
                this.b = (char) (this.b & -65);
                this.b = (char) (this.b & -129);
                this.d = ox.Top;
                textView.setTag(Float.valueOf(0.0f));
                return;
        }
    }

    private static Bitmap a(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) i, (float) (bitmap.getHeight() / 2), (float) (bitmap.getWidth() / 2));
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void a(float f) {
        ImageView imageView = (ImageView) this.c.findViewById(7);
        RelativeLayout relativeLayout = (RelativeLayout) this.c.findViewById(4);
        TextView textView = (TextView) this.c.findViewById(12);
        textView.setTag(Float.valueOf(f));
        LinearLayout linearLayout = (LinearLayout) this.c.findViewById(37);
        if ((this.b & 128) == 128) {
            linearLayout.setVisibility(8);
        } else {
            linearLayout.setVisibility(0);
        }
        Animation rotateAnimation = new RotateAnimation((float) this.d.e, f, (float) (relativeLayout.getWidth() / 2), (float) (relativeLayout.getHeight() / 2));
        rotateAnimation.setDuration(0);
        rotateAnimation.setFillAfter(true);
        Animation rotateAnimation2 = new RotateAnimation((float) this.d.e, f, (float) (textView.getWidth() / 2), (float) (textView.getHeight() / 2));
        rotateAnimation2.setDuration(0);
        rotateAnimation2.setFillAfter(true);
        Animation rotateAnimation3 = new RotateAnimation((float) this.d.e, f, (float) (imageView.getWidth() / 2), (float) (imageView.getHeight() / 2));
        rotateAnimation3.setDuration(0);
        rotateAnimation3.setFillAfter(true);
        if (this.d == ox.Left || this.d == ox.Right) {
            ((ImageView) this.c.findViewById(39)).setVisibility(4);
        } else {
            ((ImageView) this.c.findViewById(41)).setVisibility(4);
        }
        ImageView imageView2;
        if (this.d == ox.Left) {
            imageView2 = (ImageView) this.c.findViewById(41);
            imageView2.setImageBitmap(ow.a(os.a("card_example"), 90));
            imageView2.setVisibility(0);
        } else if (this.d == ox.Right) {
            imageView2 = (ImageView) this.c.findViewById(41);
            imageView2.setImageBitmap(ow.a(os.a("card_example"), -90));
            imageView2.setVisibility(0);
        } else if (this.d == ox.Top) {
            imageView2 = (ImageView) this.c.findViewById(39);
            imageView2.setImageBitmap(os.a("card_example"));
            imageView2.setVisibility(0);
        }
        textView.startAnimation(rotateAnimation2);
        imageView.startAnimation(rotateAnimation3);
        relativeLayout.startAnimation(rotateAnimation);
    }

    public final void enable() {
        super.enable();
        if (this.a != null) {
            this.a.a(this.d);
        }
        if (this.d == ox.Top) {
            a(0.0f);
        } else if (this.d == ox.Left) {
            a(90.0f);
        } else if (this.d == ox.Right) {
            a(-90.0f);
        }
    }

    public final void onOrientationChanged(int i) {
        if (i > 70 && i < 120 && this.d != ox.Right) {
            this.b = (char) (this.b | 128);
            this.b = (char) (this.b | 64);
            this.d = ox.Right;
            a(-90.0f);
            if (this.a != null) {
                this.a.a(this.d);
            }
        } else if (i > 250 && i < 290 && this.d != ox.Left) {
            this.b = (char) (this.b | 128);
            this.b = (char) (this.b & -65);
            this.d = ox.Left;
            a(90.0f);
            if (this.a != null) {
                this.a.a(this.d);
            }
        } else if (((i < 70 && i >= 0) || i > 340) && this.d != ox.Top) {
            this.b = (char) (this.b & -65);
            this.b = (char) (this.b & -129);
            this.d = ox.Top;
            a(0.0f);
            if (this.a != null) {
                this.a.a(this.d);
            }
        }
    }
}
