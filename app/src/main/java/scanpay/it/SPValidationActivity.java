package scanpay.it;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import defpackage.ov;
import defpackage.p;
import defpackage.pd;
import defpackage.w;

public class SPValidationActivity extends Activity implements OnClickListener, w {
    private SPCreditCard a;
    private View b;
    private Bitmap c;
    private pd d;

    public final void a() {
        findViewById(24).setEnabled(true);
    }

    public final void b() {
        findViewById(24).setEnabled(false);
    }

    public void onBackPressed() {
        setResult(2);
        finish();
    }

    public void onClick(View view) {
        this.a.a(this.d.a.getText().toString().replaceAll("  ", ""));
        this.a.c = this.d.b.getText().toString().substring(0, 2);
        this.a.d = this.d.b.getText().toString().substring(3, 5);
        this.a.e = this.d.c.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("creditcard", this.a);
        setResult(1, intent);
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = (SPCreditCard) getIntent().getExtras().getParcelable("creditcard");
        this.c = (Bitmap) getIntent().getExtras().getParcelable("cardImage");
        p pVar = new p(this);
        if (this.a.a.length() == 0) {
            this.b = pVar.a(true, this.a, null);
        } else {
            this.b = pVar.a(false, this.a, ov.h());
        }
        setContentView(this.b);
        this.b.findViewById(24).setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(30);
        EditText editText = (EditText) findViewById(31);
        EditText editText2 = (EditText) findViewById(29);
        EditText editText3 = (EditText) findViewById(27);
        ImageView imageView = (ImageView) findViewById(22);
        if (this.c != null) {
            Bitmap bitmap = this.c;
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(-12434878);
            canvas.drawRoundRect(rectF, 15.0f, 15.0f, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            imageView.setImageBitmap(createBitmap);
        } else {
            ((LinearLayout) findViewById(25)).setVisibility(8);
            R r = (R) findViewById(26);
            LayoutParams layoutParams = (LayoutParams) r.getLayoutParams();
            layoutParams.setMargins(0, (int) ((((float) getResources().getDisplayMetrics().densityDpi) / 160.0f) * 90.0f), 0, 0);
            r.setLayoutParams(layoutParams);
        }
        this.d = new pd(editText, editText2, editText3, (ImageView) findViewById(32), this);
        this.d.d = this;
        if (this.a.a.length() > 0) {
            pd pdVar = this.d;
            SPCreditCard sPCreditCard = this.a;
            if (sPCreditCard.c != null && sPCreditCard.c.length() > 0) {
                pdVar.f = Boolean.valueOf(true);
            }
            pdVar.e = 0;
            pdVar.a.setText(sPCreditCard.a().replaceAll(" ", "  "));
            pdVar.a(pdVar.a.getEditableText());
            if (pdVar.f.booleanValue()) {
                pdVar.b.removeTextChangedListener(pdVar);
                pdVar.b.setText(sPCreditCard.c + "/" + sPCreditCard.d);
                pdVar.g = 5;
                pdVar.b.addTextChangedListener(pdVar);
                pdVar.b(pdVar.b.getEditableText());
            }
        }
        Drawable gradientDrawable = new GradientDrawable(Orientation.BOTTOM_TOP, new int[]{-1, -1});
        gradientDrawable.setStroke(2, Color.parseColor("#B7B7B7"));
        gradientDrawable.setCornerRadius(7.0f);
        if (VERSION.SDK_INT >= 16) {
            linearLayout.setBackground(gradientDrawable);
        } else {
            linearLayout.setBackgroundDrawable(gradientDrawable);
        }
    }
}
