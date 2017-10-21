package defpackage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public final class R extends RelativeLayout {
    private Context a;

    public R(Context context) {
        super(context);
        this.a = context;
        setId(26);
        setGravity(1);
        View linearLayout = new LinearLayout(this.a);
        linearLayout.setId(30);
        linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        linearLayout.setGravity(16);
        linearLayout.setOrientation(0);
        linearLayout.setLayoutParams(new LayoutParams((int) a(BitmapDescriptorFactory.HUE_MAGENTA), (int) a(45.0f)));
        Drawable gradientDrawable = new GradientDrawable(Orientation.BOTTOM_TOP, new int[]{-1, -1});
        gradientDrawable.setStroke(2, Color.parseColor("#B7B7B7"));
        gradientDrawable.setCornerRadius(7.0f);
        if (VERSION.SDK_INT >= 16) {
            linearLayout.setBackground(gradientDrawable);
        } else {
            linearLayout.setBackgroundDrawable(gradientDrawable);
        }
        View imageView = new ImageView(this.a);
        imageView.setId(32);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) a(35.0f), (int) a(35.0f));
        layoutParams.setMargins((int) a(10.0f), 0, (int) a(10.0f), 0);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageBitmap(os.a("basic_card"));
        linearLayout.addView(imageView);
        imageView = new RelativeLayout(this.a);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout.addView(imageView);
        View editText = new EditText(this.a);
        editText.setId(31);
        editText.setRawInputType(3);
        if (VERSION.SDK_INT >= 16) {
            editText.setBackground(null);
        } else {
            editText.setBackgroundDrawable(null);
        }
        editText.setHint("1234  5678  9101  1234");
        editText.setTextSize(18.0f);
        editText.setPadding(0, 0, 0, 0);
        editText.setGravity(16);
        editText.setTypeface(null, 1);
        editText.setLayoutParams(new LayoutParams((int) a(220.0f), (int) a(45.0f)));
        imageView.addView(editText);
        editText = new EditText(this.a);
        editText.setId(29);
        editText.setRawInputType(3);
        if (VERSION.SDK_INT >= 16) {
            editText.setBackground(null);
        } else {
            editText.setBackgroundDrawable(null);
        }
        editText.setHint("MM/YY");
        editText.setTextSize(18.0f);
        editText.setTypeface(null, 1);
        editText.setPadding(0, 0, 0, 0);
        editText.setGravity(17);
        ViewGroup.LayoutParams layoutParams2 = new LayoutParams((int) a(100.0f), (int) a(45.0f));
        layoutParams2.addRule(11);
        layoutParams2.setMargins(0, 0, (int) a(85.0f), 0);
        editText.setLayoutParams(layoutParams2);
        editText.setVisibility(8);
        imageView.addView(editText);
        editText = new EditText(this.a);
        editText.setId(27);
        editText.setRawInputType(3);
        if (VERSION.SDK_INT >= 16) {
            editText.setBackground(null);
        } else {
            editText.setBackgroundDrawable(null);
        }
        editText.setHint("CVV");
        editText.setTextSize(18.0f);
        editText.setTypeface(null, 1);
        editText.setPadding(0, 0, 0, 0);
        editText.setGravity(17);
        layoutParams2 = new LayoutParams((int) a(65.0f), (int) a(45.0f));
        layoutParams2.addRule(11);
        layoutParams2.setMargins(0, 0, (int) a(20.0f), 0);
        editText.setLayoutParams(layoutParams2);
        editText.setVisibility(8);
        imageView.addView(editText);
        addView(linearLayout);
    }

    private float a(float f) {
        return (((float) this.a.getResources().getDisplayMetrics().densityDpi) / 160.0f) * f;
    }
}
